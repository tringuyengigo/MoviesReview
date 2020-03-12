package gdsvn.tringuyen.moviesreview.data.responsitory


import gdsvn.tringuyen.moviesreview.data.local.model.Movies
import gdsvn.tringuyen.moviesreview.data.remote.api.MoviesService
import gdsvn.tringuyen.moviesreview.domain.respository.MoviesRepository
import gdsvn.tringuyen.moviesreview.presentation.common.MoviesFilterType
import io.reactivex.Flowable
import timber.log.Timber

class MoviesRemoteRepositoryImpl constructor(private val apiService: MoviesService) : MoviesRepository {

    override fun getPopularMovies(page: Int): Flowable<Movies?> {
        return apiService.getPopularMovies(page = page)
    }

}