package gdsvn.tringuyen.moviesreview.domain.usecase

import gdsvn.tringuyen.moviesreview.data.local.model.moviedetail.MovieDetail
import gdsvn.tringuyen.moviesreview.data.local.model.movies.Movies
import gdsvn.tringuyen.moviesreview.data.responsitory.MoviesRemoteRepositoryImpl
import gdsvn.tringuyen.moviesreview.domain.common.BaseFlowableUseCase
import gdsvn.tringuyen.moviesreview.domain.common.FlowableRxTransformer
import io.reactivex.Single


class GetMovieDetailUseCase(private val transformer: FlowableRxTransformer<MovieDetail?>,
                            private val repositories: MoviesRemoteRepositoryImpl
) : BaseFlowableUseCase<MovieDetail?>(transformer){


    companion object {
        private val TAG = "GetMovieDetailUseCase"
    }
    override fun createFlowable(data: Map<String, Any>?): Single<MovieDetail?> {
        return repositories.getMovieDetail(data?.get("id") as Long)
    }

    fun getMovieDetail(id: Long) : Single<MovieDetail?> {
        val data = HashMap<String, Long>()
        data["id"] = id
        return single(data)
    }

}