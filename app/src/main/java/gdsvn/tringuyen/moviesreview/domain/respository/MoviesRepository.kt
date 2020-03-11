package gdsvn.tringuyen.moviesreview.domain.respository

import gdsvn.tringuyen.moviesreview.data.local.model.Movies
import io.reactivex.Flowable

interface MoviesRepository {
    fun getPopularMovies(page: Int): Flowable<Movies?>
}


