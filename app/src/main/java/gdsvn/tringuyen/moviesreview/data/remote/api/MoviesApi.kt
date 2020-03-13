package gdsvn.tringuyen.moviesreview.data.remote.api

import gdsvn.tringuyen.moviesreview.data.local.model.Movies
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("3/movie/popular")
    fun getPopularMovies(@Query("page") page: Int) : Single<Movies?>

}