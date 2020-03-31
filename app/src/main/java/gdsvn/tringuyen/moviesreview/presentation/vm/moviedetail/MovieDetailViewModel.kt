package gdsvn.tringuyen.moviesreview.presentation.vm.moviedetail

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import gdsvn.tringuyen.moviesreview.R
import gdsvn.tringuyen.moviesreview.data.local.model.moviedetail.MovieDetail
import gdsvn.tringuyen.moviesreview.domain.usecase.GetMovieDetailUseCase
import gdsvn.tringuyen.moviesreview.presentation.common.base.BaseViewModel
import gdsvn.tringuyen.moviesreview.presentation.common.define.Data
import gdsvn.tringuyen.moviesreview.presentation.common.define.Status
import gdsvn.tringuyen.moviesreview.presentation.common.utils.SnackbarMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MovieDetailViewModel (private val getMovieDetailUseCase: GetMovieDetailUseCase) : BaseViewModel() {

    private var mMovieDetail = MutableLiveData<Data<MovieDetail>>()
    fun getMovieDetailLiveData() = mMovieDetail

    private val mSnackbarText: SnackbarMessage = SnackbarMessage()

    private var isFavorite = false

    fun fetchMovieDetail(id: Long) {
        val disposable = getMovieDetailUseCase.getMovieDetail(id = id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
            {  itemMovieDetail ->
                Timber.e("fetchMovieDetail ${Gson().toJson(itemMovieDetail)}")
                mMovieDetail.value = Data(responseType = Status.SUCCESSFUL, data = itemMovieDetail)
            },
            { error ->
                Timber.e("Exception $error")
                mMovieDetail.value = Data(responseType = Status.ERROR, error = Error(error.message))
            }
        )
        addDisposable(disposable)
    }


    fun isFavorite(): Boolean {
        return isFavorite
    }

    // Will update
    fun onFavoriteClicked() {
//        val movieDetails: MovieDetails = result.getValue().data
        if (!isFavorite) {
//            repository.favoriteMovie(movieDetails.getMovie())
            isFavorite = true
            showSnackbarMessage(R.string.movie_added_successfully)
        } else {
//            repository.unfavoriteMovie(movieDetails.getMovie())
            isFavorite = false
            showSnackbarMessage(R.string.movie_removed_successfully)
        }
    }

    private fun showSnackbarMessage(message: Int) {
        mSnackbarText.setValue(message)
    }


}