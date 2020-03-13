package gdsvn.tringuyen.moviesreview.domain.respository

import gdsvn.tringuyen.moviesreview.data.local.model.Movies
import io.reactivex.Flowable
import io.reactivex.Single

interface MoviesRepository {
    fun getPopularMovies(page: Int): Single<Movies?>
}


