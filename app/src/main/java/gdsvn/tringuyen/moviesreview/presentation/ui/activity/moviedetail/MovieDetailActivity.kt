package gdsvn.tringuyen.moviesreview.presentation.ui.activity.moviedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import gdsvn.tringuyen.moviesreview.R
import gdsvn.tringuyen.moviesreview.presentation.common.define.Define
import gdsvn.tringuyen.moviesreview.presentation.vm.moviedetail.MovieDetailViewModel
import gdsvn.tringuyen.moviesreview.presentation.vm.popular.MoviesPopularViewModel
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlinx.android.synthetic.main.partial_details_info.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MovieDetailActivity : AppCompatActivity() {

    private val movieDetailViewModel: MovieDetailViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movieDetailViewModel.fetchMovieDetail(id = 181812)

        movieDetailViewModel.getMovieDetailLiveData().observe(this, Observer { itemMovie ->
            val backdropURL = Define.URL_LOAD_IMAGE + itemMovie.data?.backdrop_path
            Glide.with(this)
                .load(backdropURL)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(image_movie_backdrop)
            val posterURL = Define.URL_LOAD_IMAGE + itemMovie.data?.poster_path
            Glide.with(this)
                .load(posterURL)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(image_poster)

            text_title.text = itemMovie.data?.title
            var i = 0
//            for (index in itemMovie.data?.genres!!) {
//                i += 1
                Timber.e("item - ${itemMovie.data?.genres!![1].name}")

//                val chip = Chip(this)
//                chip.text = itemMovie.data?.genres!![i].name
//
//                // necessary to get single selection working
//                chip.isClickable = true
//                chip.isCheckable = true
//                chip_group.addView(chip)
//            }

        })

    }
}