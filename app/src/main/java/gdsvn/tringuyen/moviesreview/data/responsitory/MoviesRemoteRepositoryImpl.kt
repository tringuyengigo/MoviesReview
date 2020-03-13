package gdsvn.tringuyen.moviesreview.data.responsitory


import gdsvn.tringuyen.moviesreview.data.local.model.Movies
import gdsvn.tringuyen.moviesreview.data.remote.api.MoviesApi
import gdsvn.tringuyen.moviesreview.domain.respository.MoviesRepository
import io.reactivex.Flowable
import io.reactivex.Single

class MoviesRemoteRepositoryImpl constructor(private val apiService: MoviesApi) : MoviesRepository {

    override fun getPopularMovies(page: Int): Single<Movies?> {
        return apiService.getPopularMovies(page = page)
    }

}