package gdsvn.tringuyen.moviesreview.domain.common

import gdsvn.tringuyen.moviesreview.data.local.model.Movies
import io.reactivex.Flowable
import io.reactivex.Single

abstract class BaseFlowableUseCase<T>(private val transformer: FlowableRxTransformer<T>) {

    abstract fun createFlowable(data: Map<String, Any>? = null): Single<T>

    fun single(withData: Map<String, Any>? = null): Single<T> {
        return createFlowable(withData)
    }
}