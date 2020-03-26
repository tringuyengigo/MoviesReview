package gdsvn.tringuyen.moviesreview.domain.respository

import gdsvn.tringuyen.moviesreview.data.local.model.movies.Movies
import io.reactivex.Single

interface MoviesRepository {
    fun getPopularMovies(page: Int): Single<Movies?>
}


