package gdsvn.tringuyen.moviesreview.presentation.vm.popular

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import gdsvn.tringuyen.moviesreview.data.local.model.Movie
import gdsvn.tringuyen.moviesreview.data.local.model.Movies
import gdsvn.tringuyen.moviesreview.data.remote.paging.MoviesDataSource
import gdsvn.tringuyen.moviesreview.data.remote.paging.MoviesDataSourceFactory
import gdsvn.tringuyen.moviesreview.data.remote.paging.State
import gdsvn.tringuyen.moviesreview.domain.usecase.GetMoviesPopularUseCase
import gdsvn.tringuyen.moviesreview.presentation.common.BaseViewModel
import gdsvn.tringuyen.moviesreview.presentation.common.Data
import gdsvn.tringuyen.moviesreview.presentation.common.Status
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber


class MoviesPopularViewModel(private val getMoviesPopularUseCase: GetMoviesPopularUseCase) : BaseViewModel() {

    private var mMovies = MutableLiveData<Data<Movies>>()
    fun getMoviesPopularLiveData() = mMovies

    var movieList: LiveData<PagedList<Movie>>

    private val pageSize = 5
    private val mMoviesDataSourceFactory: MoviesDataSourceFactory = MoviesDataSourceFactory(compositeDisposable, getMoviesPopularUseCase)

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(true)
            .build()
        movieList = LivePagedListBuilder(mMoviesDataSourceFactory, config).build()

    }

    fun retry() {
        mMoviesDataSourceFactory.moviesDataSourceLiveData.value?.retry()
    }

    fun getState(): LiveData<State> = Transformations.switchMap<MoviesDataSource,
            State>(mMoviesDataSourceFactory.moviesDataSourceLiveData, MoviesDataSource::state)


    fun listIsEmpty(): Boolean {
        return movieList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}

