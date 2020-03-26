package gdsvn.tringuyen.moviesreview.data.remote.api

import androidx.lifecycle.LiveData
import gdsvn.tringuyen.moviesreview.data.local.model.moviedetail.MovieDetail
import gdsvn.tringuyen.moviesreview.data.local.model.movies.Movies
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("3/movie/popular")
    fun getPopularMovies(@Query("page") page: Int) : Single<Movies?>


    /**
     * Instead of using 4 separate requests we use append_to_response
     * to eliminate duplicate requests and save network bandwidth
     * this request return full movie details, trailers, reviews and cast
     */
    @GET("3/movie/{id}?append_to_response=videos,credits,reviews")
    fun getMovieDetail(@Path("id") id: Long) : Single<MovieDetail?>




}