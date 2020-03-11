package gdsvn.tringuyen.moviesreview.domain.usecase

import gdsvn.tringuyen.moviesreview.data.local.model.Movies
import gdsvn.tringuyen.moviesreview.data.responsitory.MoviesRemoteRepositoryImpl
import gdsvn.tringuyen.moviesreview.data.responsitory.MoviesRepositoryImpl
import gdsvn.tringuyen.moviesreview.domain.common.BaseFlowableUseCase
import gdsvn.tringuyen.moviesreview.domain.common.FlowableRxTransformer
import gdsvn.tringuyen.moviesreview.domain.respository.MoviesRepository
import io.reactivex.Flowable


class GetMoviesPopularUseCase(private val transformer: FlowableRxTransformer<Movies?>,
                              private val repositories: MoviesRemoteRepositoryImpl
) : BaseFlowableUseCase<Movies?>(transformer){


    companion object {
        private val TAG = "GetMoviesPopularUseCase"
    }
    override fun createFlowable(data: Map<String, Any>?): Flowable<Movies?> {
        return repositories.getPopularMovies(data?.get("page") as Int)
    }

    fun getPopularMovies(page: Int) : Flowable<Movies?> {
        val data = HashMap<String, Int>()
        data["page"] = page
        return single(data)
    }

}