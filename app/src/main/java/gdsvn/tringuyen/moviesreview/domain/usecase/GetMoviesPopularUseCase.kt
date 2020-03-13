package gdsvn.tringuyen.moviesreview.domain.usecase

import gdsvn.tringuyen.moviesreview.data.local.model.Movies
import gdsvn.tringuyen.moviesreview.data.responsitory.MoviesRemoteRepositoryImpl
import gdsvn.tringuyen.moviesreview.domain.common.BaseFlowableUseCase
import gdsvn.tringuyen.moviesreview.domain.common.FlowableRxTransformer
import io.reactivex.Flowable
import io.reactivex.Single


class GetMoviesPopularUseCase(private val transformer: FlowableRxTransformer<Movies?>,
                              private val repositories: MoviesRemoteRepositoryImpl
) : BaseFlowableUseCase<Movies?>(transformer){


    companion object {
        private val TAG = "GetMoviesPopularUseCase"
    }
    override fun createFlowable(data: Map<String, Any>?): Single<Movies?> {
        return repositories.getPopularMovies(data?.get("page") as Int)
    }

    fun getPopularMovies(page: Int) : Single<Movies?> {
        val data = HashMap<String, Int>()
        data["page"] = page
        return single(data)
    }

}