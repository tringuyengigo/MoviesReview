package gdsvn.tringuyen.moviesreview.data.local.model.movies

import java.util.*

/**
 * A generic class that contains data and status about loading this data.
 *
 *
 * You can read more about it in the [Architecture Guide]
 * (https://developer.android.com/jetpack/docs/guide#addendum).
 */
class Resource<Any> private constructor(
    val status: Status, val data: kotlin.Any?,
    val message: String?
) {
    override fun equals(other: kotlin.Any?): Boolean {
        if (this === other) return true
        if (other !is Resource<*>) return false
        return !(status != other.status || data != other.data || message != other.message)
    }

    override fun hashCode(): Int {
        return Objects.hash(status, data, message)
    }

    /**
     * Status of a resource that is provided to the UI.
     *
     *
     * These are usually created by the Repository classes where they return
     * `LiveData<Resource></Resource><T>>` to pass back the latest data to the UI with its fetch status.
    </T> */
    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        /**
         * Creates [Resource] object with `SUCCESS` status and [data].
         */
        fun <T> success(data: T): Resource<Any>? {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        /**
         * Creates [Resource] object with `ERROR` status and [message].
         */
        fun <T> error(
            msg: String?,
            data: T?
        ): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                msg
            )
        }

        /**
         * Creates [Resource] object with `LOADING` status to notify
         * the UI to show loading.
         */
        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
    }

}