package gdsvn.tringuyen.moviesreview.domain.respository

import gdsvn.tringuyen.moviesreview.data.local.model.moviedetail.MovieDetail
import gdsvn.tringuyen.moviesreview.data.local.model.movies.Movies
import io.reactivex.Single

interface MovieDetailRepository {
    fun getMovieDetail(id: Long): Single<MovieDetail?>
}


