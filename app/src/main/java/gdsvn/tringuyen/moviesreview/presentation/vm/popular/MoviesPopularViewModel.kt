package gdsvn.tringuyen.moviesreview.presentation.vm.popular

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import gdsvn.tringuyen.moviesreview.data.local.model.movies.Movie
import gdsvn.tringuyen.moviesreview.data.local.model.movies.Movies
import gdsvn.tringuyen.moviesreview.data.remote.paging.common.MoviesDataSource
import gdsvn.tringuyen.moviesreview.data.remote.paging.common.MoviesDataSourceFactory
import gdsvn.tringuyen.moviesreview.data.remote.paging.common.State
import gdsvn.tringuyen.moviesreview.domain.usecase.GetMoviesPopularUseCase
import gdsvn.tringuyen.moviesreview.presentation.common.base.BaseViewModel
import gdsvn.tringuyen.moviesreview.presentation.common.define.Data
import gdsvn.tringuyen.moviesreview.presentation.common.utils.BarcodeFactory


class MoviesPopularViewModel(private val getMoviesPopularUseCase: GetMoviesPopularUseCase,
                             private val barcodeObject: BarcodeFactory) : BaseViewModel() {

    private var mMovies = MutableLiveData<Data<Movies>>()
    fun getMoviesPopularLiveData() = mMovies

    var movieList: LiveData<PagedList<Movie>>

    private val pageSize = 5
    private val mMoviesDataSourceFactory: MoviesDataSourceFactory =
        MoviesDataSourceFactory(
            compositeDisposable,
            getMoviesPopularUseCase
        )

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

    fun processBarcodeUser( stringID: String,
                            widthPixels: Int,
                            heightPixels: Int,
                            barcodeColor: Int,
                            backgroundColor: Int) : Bitmap {
       return barcodeObject.createBarcodeBitmap (
            barcodeValue = stringID,
            barcodeColor = barcodeColor,
            backgroundColor = backgroundColor,
            widthPixels = widthPixels,
            heightPixels = heightPixels
        )
    }

}

