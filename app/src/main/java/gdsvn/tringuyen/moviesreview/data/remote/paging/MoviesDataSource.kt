package gdsvn.tringuyen.moviesreview.data.remote.paging

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import gdsvn.tringuyen.moviesreview.data.local.model.Movie
import gdsvn.tringuyen.moviesreview.data.local.model.Movies
import gdsvn.tringuyen.moviesreview.domain.usecase.GetMoviesPopularUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MoviesDataSource(
        private val mGetMoviesPopularUseCase: GetMoviesPopularUseCase,
        private val compositeDisposable: CompositeDisposable) : PageKeyedDataSource<Int, Movie>() {

    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null


    @SuppressLint("CheckResult")
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        updateState(State.LOADING)
        compositeDisposable.add(

            mGetMoviesPopularUseCase.getPopularMovies(1)
                .subscribe(
                    { response ->
                        Timber.e("loadInitial ${response?.results}")
                        updateState(State.DONE)
                        Timber.e("loadInitial state ${state.value}")
                        response?.results?.let {
                            Timber.e("loadInitial in response ${it.forEach {
                                Timber.e("loadInitial item ${it} ") 
                            }}")
                            callback.onResult(
                                it,
                                null,
                                2
                            )
                        }
                    },
                    {
                        updateState(State.ERROR)
                        setRetry(Action { loadInitial(params, callback) })
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            mGetMoviesPopularUseCase.getPopularMovies(params.key)
                        .subscribe(
                                { response ->
                                    updateState(State.DONE)
                                    response?.results?.let {
                                        callback.onResult(
                                            it,
                                            params.key + 1
                                        )
                                    }
                                },
                                {
                                    updateState(State.ERROR)
                                    setRetry(Action { loadAfter(params, callback) })
                                }
                        )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe())
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

}