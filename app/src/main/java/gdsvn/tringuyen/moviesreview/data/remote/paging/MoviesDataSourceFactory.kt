package gdsvn.tringuyen.moviesreview.data.remote.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import gdsvn.tringuyen.moviesreview.data.local.model.Movie
import gdsvn.tringuyen.moviesreview.domain.usecase.GetMoviesPopularUseCase
import io.reactivex.disposables.CompositeDisposable


class MoviesDataSourceFactory(
        private val compositeDisposable: CompositeDisposable,
        private val mGetMoviesPopularUseCase: GetMoviesPopularUseCase
) : DataSource.Factory<Int, Movie>() {

    val moviesDataSourceLiveData = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, Movie> {
        var moviesDataSource = MoviesDataSource(mGetMoviesPopularUseCase, compositeDisposable)
        moviesDataSourceLiveData.postValue(moviesDataSource)
        return moviesDataSource
    }

}