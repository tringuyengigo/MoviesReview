package gdsvn.tringuyen.moviesreview.data.responsitory


import gdsvn.tringuyen.moviesreview.data.local.model.Movies
import gdsvn.tringuyen.moviesreview.domain.respository.MoviesRepository
import io.reactivex.Flowable
import timber.log.Timber

class MoviesRepositoryImpl(private val remote: MoviesRemoteRepository) : MoviesRepository {
    override fun getPopularMovies(page: Int): Flowable<Movies?> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
