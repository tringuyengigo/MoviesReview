package gdsvn.tringuyen.moviesreview.data.responsitory

import gdsvn.tringuyen.moviesreview.data.local.model.movies.Movies
import io.reactivex.Flowable

interface MoviesRemoteRepository {
    fun getPopularMovies(page: Int): Flowable<Movies?>
}