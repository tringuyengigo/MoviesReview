package gdsvn.tringuyen.moviesreview.presentation.ui.vm.popular

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import gdsvn.tringuyen.moviesreview.data.local.model.Movies
import gdsvn.tringuyen.moviesreview.domain.usecase.GetMoviesPopularUseCase
import gdsvn.tringuyen.moviesreview.presentation.common.BaseViewModel
import gdsvn.tringuyen.moviesreview.presentation.common.Data
import gdsvn.tringuyen.moviesreview.presentation.common.Status
import timber.log.Timber

class MoviesPopularViewModel(private val getMoviesPopularUseCase: GetMoviesPopularUseCase) : BaseViewModel() {

    private var mMovies = MutableLiveData<Data<Movies>>()
    fun getMoviesPopularLiveData() = mMovies

    @SuppressLint("CheckResult")
    fun fetchMoviesPopular() {
        getMoviesPopularUseCase.getPopularMovies(page = 1).subscribe({ response ->
            mMovies.value = Data(responseType = Status.SUCCESSFUL, data = response)
        }, { error ->
            mMovies.value = Data(responseType = Status.ERROR, error = Error(error.message))
        }, {
            Timber.d("On fetchWeatherCity() Complete Called")
            this.onCleared()
        })
    }
}

