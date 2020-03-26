package gdsvn.tringuyen.moviesreview.presentation.vm.moviedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import gdsvn.tringuyen.moviesreview.data.local.model.moviedetail.MovieDetail
import gdsvn.tringuyen.moviesreview.data.local.model.movies.Movies
import gdsvn.tringuyen.moviesreview.data.remote.paging.common.State
import gdsvn.tringuyen.moviesreview.domain.usecase.GetMovieDetailUseCase
import gdsvn.tringuyen.moviesreview.domain.usecase.GetMoviesPopularUseCase
import gdsvn.tringuyen.moviesreview.presentation.common.base.BaseViewModel
import gdsvn.tringuyen.moviesreview.presentation.common.define.Data
import gdsvn.tringuyen.moviesreview.presentation.common.define.Status
import gdsvn.tringuyen.moviesreview.presentation.common.utils.BarcodeFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.annotations.Async
import timber.log.Timber

class MovieDetailViewModel (private val getMovieDetailUseCase: GetMovieDetailUseCase) : BaseViewModel() {

    private var mMovieDetail = MutableLiveData<Data<MovieDetail>>()
    fun getMovieDetailLiveData() = mMovieDetail

    fun fetchMovieDetail(id: Long) {
        val disposable = getMovieDetailUseCase.getMovieDetail(id = 495764)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
            {  itemMovieDetail ->
                Timber.e("fetchMovieDetail ${Gson().toJson(itemMovieDetail)}")
                mMovieDetail.value = Data(responseType = Status.SUCCESSFUL, data = itemMovieDetail)
            },
            {
                Timber.e("Exception $it")
            }
        )
        addDisposable(disposable)
    }


}