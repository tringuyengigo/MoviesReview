package gdsvn.tringuyen.moviesreview.presentation.vm.popular

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import gdsvn.tringuyen.moviesreview.data.local.model.Movie
import gdsvn.tringuyen.moviesreview.data.local.model.Movies
import gdsvn.tringuyen.moviesreview.data.remote.paging.MoviesDataSourceFactory
import gdsvn.tringuyen.moviesreview.domain.usecase.GetMoviesPopularUseCase
import gdsvn.tringuyen.moviesreview.presentation.common.BaseViewModel
import gdsvn.tringuyen.moviesreview.presentation.common.Data
import gdsvn.tringuyen.moviesreview.presentation.common.Status
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber


class MoviesPopularViewModel(private val getMoviesPopularUseCase: GetMoviesPopularUseCase) : BaseViewModel() {

    private var mMovies = MutableLiveData<Data<Movies>>()
    fun getMoviesPopularLiveData() = mMovies

    lateinit var movieList: LiveData<PagedList<Movie>>

    private val pageSize = 1
    private val mMoviesDataSourceFactory: MoviesDataSourceFactory = MoviesDataSourceFactory(compositeDisposable, getMoviesPopularUseCase)

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        movieList = LivePagedListBuilder(mMoviesDataSourceFactory, config).build()

    }

    fun retry() {
        mMoviesDataSourceFactory.moviesDataSourceLiveData.value?.retry()
    }

    @SuppressLint("CheckResult")
    fun fetchMoviesPopular() {


//        getMoviesPopularUseCase.getPopularMovies(page = 1).subscribe({ response ->
//            mMovies.value = Data(responseType = Status.SUCCESSFUL, data = response)
//        }, { error ->
//            mMovies.value = Data(responseType = Status.ERROR, error = Error(error.message))
//        }, {
//            Timber.d("On Complete Called")
//        })
    }
}

