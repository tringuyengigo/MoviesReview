package gdsvn.tringuyen.moviesreview.data.responsitory


import gdsvn.tringuyen.moviesreview.data.local.model.Movies
import gdsvn.tringuyen.moviesreview.data.network.api.MoviesService
import gdsvn.tringuyen.moviesreview.domain.respository.MoviesRepository
import io.reactivex.Flowable

class MoviesRemoteRepositoryImpl constructor(private val apiService: MoviesService) : MoviesRepository {

    override fun getPopularMovies(page: Int): Flowable<Movies?> {
        return apiService.getPopularMovies(page = page)
    }

}