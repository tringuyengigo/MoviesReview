package gdsvn.tringuyen.moviesreview.data.responsitory


import gdsvn.tringuyen.moviesreview.data.local.model.moviedetail.MovieDetail
import gdsvn.tringuyen.moviesreview.data.local.model.movies.Movies
import gdsvn.tringuyen.moviesreview.data.remote.api.MoviesApi
import gdsvn.tringuyen.moviesreview.domain.respository.MovieDetailRepository
import gdsvn.tringuyen.moviesreview.domain.respository.MoviesRepository
import io.reactivex.Single

class MoviesRemoteRepositoryImpl constructor(private val apiService: MoviesApi) : MoviesRepository, MovieDetailRepository {

    override fun getPopularMovies(page: Int): Single<Movies?> {
        return apiService.getPopularMovies(page = page)
    }

    override fun getMovieDetail(id: Long): Single<MovieDetail?> {
        return apiService.getMovieDetail(id = id)
    }


}