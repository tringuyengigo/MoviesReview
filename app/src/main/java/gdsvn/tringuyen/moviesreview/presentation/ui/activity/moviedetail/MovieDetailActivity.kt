package gdsvn.tringuyen.moviesreview.presentation.ui.activity.moviedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ShareCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.chip.Chip
import gdsvn.tringuyen.moviesreview.R
import gdsvn.tringuyen.moviesreview.data.local.model.moviedetail.MovieDetail
import gdsvn.tringuyen.moviesreview.presentation.common.define.Define
import gdsvn.tringuyen.moviesreview.presentation.common.define.Status
import gdsvn.tringuyen.moviesreview.presentation.common.utils.UiUtils
import gdsvn.tringuyen.moviesreview.presentation.ui.activity.book.BookingTicketActivity
import gdsvn.tringuyen.moviesreview.presentation.ui.activity.moviedetail.adapters.cast.CastAdapter
import gdsvn.tringuyen.moviesreview.presentation.ui.activity.moviedetail.adapters.reviews.ReviewsAdapter
import gdsvn.tringuyen.moviesreview.presentation.ui.activity.moviedetail.adapters.trailer.TrailersAdapter
import gdsvn.tringuyen.moviesreview.presentation.ui.activity.splash.SplashScreen
import gdsvn.tringuyen.moviesreview.presentation.vm.moviedetail.MovieDetailViewModel
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.activity_movie_detail.appbar
import kotlinx.android.synthetic.main.activity_movie_detail.collapsing_toolbar
import kotlinx.android.synthetic.main.activity_movie_detail.toolbar
import kotlinx.android.synthetic.main.movies_fragment.*
import kotlinx.android.synthetic.main.movies_fragment_main_layout.*
import kotlinx.android.synthetic.main.partial_details_info.*
import org.koin.android.viewmodel.ext.android.viewModel


class MovieDetailActivity : AppCompatActivity() {

    private val movieDetailViewModel: MovieDetailViewModel by viewModel()
    private lateinit var castAdapter: CastAdapter
    private lateinit var trailersAdapter: TrailersAdapter
    private lateinit var reviewsAdapter: ReviewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setupToolbar()
        initiliseMovieDetail()
        processEvents()

    }

    private fun processEvents() {
        ///Booking
        bt_book.setOnClickListener {
            startActivity(Intent(this@MovieDetailActivity, BookingTicketActivity::class.java))
        }
    }

    private fun initiliseMovieDetail() {
        val movieId = intent.getLongExtra(
            Define.EXTRA_MOVIE_ID,
            Define.DEFAULT_ID.toLong()
        )
        if (movieId == Define.DEFAULT_ID.toLong()) {
            closeOnError()
            return
        }
        movieDetailViewModel.fetchMovieDetail(id = movieId)
        movieDetailViewModel.getMovieDetailLiveData().observe(this, Observer { data ->
            when (data?.responseType) {
                Status.ERROR -> {
                    showState(true, this.getString(R.string.error_retry))
                }
                Status.LOADING -> {
                    showState(true, getString(R.string.loading_data))
                }
                Status.SUCCESSFUL -> {
                    showState(false, getString(R.string.successfully))
                }
            }
            data?.data?.let { itemMovie ->
                ///////////////////////////////////
                text_title.text = itemMovie.title
                ///////////////////////////////////
                val backdropURL = Define.BACKDROP_URL + itemMovie.backdrop_path
                Glide.with(this)
                    .load(backdropURL)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(image_movie_backdrop)
                ///////////////////////////////////
                val posterURL = Define.IMAGE_URL + itemMovie.poster_path
                Glide.with(this)
                    .load(posterURL)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(image_poster)

                ///////////////////////////////////
                for (element in itemMovie.genres) {
                    val chip = Chip(this)
                    chip.text = element.name
                    // necessary to get single selection working
                    chip.isClickable = true
                    chip.isCheckable = true
                    chip_group.addView(chip)
                }
                //////////////////////////////////
                text_vote.text = itemMovie.vote_average.toString() + "/" + Define.TEN_MARK
                label_vote.text = itemMovie.vote_count.toString()
                /////////////////////////////////
                text_language.text = itemMovie.original_language
                ///////////////////////////////
                text_release_date.text = itemMovie.release_date
                ///////////////////////////////
                text_overview.text = itemMovie.overview
                ///////////////////////////////
                setupCastAdapter(itemMovie)
                ///////////////////////////////
                setupTrailersAdapter(itemMovie)
                /////////////////////////////
                setupReviewsAdapter(itemMovie)
            }

        })
    }

    private fun setupToolbar() {
        this.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        this.supportActionBar?.hide()
        val toolbar: Toolbar = toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            handleCollapsedToolbarTitle()
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupCastAdapter(itemMovie: MovieDetail) {
        val rcvListCast: RecyclerView = list_cast
        rcvListCast.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        castAdapter = CastAdapter()
        castAdapter.submitList(itemMovie.credits.cast)
        rcvListCast.adapter = castAdapter
        ViewCompat.setNestedScrollingEnabled(rcvListCast, false)
    }

    private fun setupTrailersAdapter(itemMovie: MovieDetail) {
        val rcvListTrailer: RecyclerView = list_trailers
        rcvListTrailer.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        trailersAdapter = TrailersAdapter()
        trailersAdapter.submitList(itemMovie.videos.Trailers)
        rcvListTrailer.adapter = trailersAdapter
        ViewCompat.setNestedScrollingEnabled(rcvListTrailer, false)
    }

    private fun setupReviewsAdapter(itemMovie: MovieDetail) {
        val rcvListReview: RecyclerView = list_reviews
        rcvListReview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        reviewsAdapter = ReviewsAdapter()
        reviewsAdapter.submitList(itemMovie.reviews.reviews)
        rcvListReview.adapter = reviewsAdapter
        ViewCompat.setNestedScrollingEnabled(rcvListReview, false)
    }


    private fun showState(isShow: Boolean, string: String) {
       if(isShow) {
           progress_bar.visibility = View.VISIBLE
           txt_error.visibility = View.VISIBLE
       } else {
           progress_bar.visibility = View.GONE
           txt_error.visibility = View.GONE
       }
        txt_error.text = string
    }

    private fun closeOnError() {
        throw IllegalArgumentException("Access denied.")
    }


    /**
     * sets the title on the toolbar only if the toolbar is collapsed
     */
    private fun handleCollapsedToolbarTitle() {
        appbar.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                // verify if the toolbar is completely collapsed and set the movie name as the title
                if (scrollRange + verticalOffset == 0) {
                    collapsing_toolbar.title = movieDetailViewModel.getMovieDetailLiveData().value?.data?.title
                    collapsing_toolbar.setCollapsedTitleTextColor(resources.getColor(R.color.text_light))
                    toolbar.setBackgroundColor(resources.getColor(R.color.classic_darkTheme_colorBackground))
                    isShow = true
                } else if (isShow) {
                    // display an empty string when toolbar is expanded
                    collapsing_toolbar.title = " "
                    collapsing_toolbar.setCollapsedTitleTextColor(resources.getColor(R.color.text_light))
                    toolbar.setBackgroundColor(resources.getColor(R.color.colorBackground))
                    isShow = false
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                val shareIntent = ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setSubject(movieDetailViewModel.getMovieDetailLiveData().value?.data?.title + " movie trailer")
                    .setText(
                        "Check out " + movieDetailViewModel.getMovieDetailLiveData().value?.data?.title + " movie trailer at " +
                                Uri.parse(
                                Define.YOUTUBE_WEB_URL +
                                        movieDetailViewModel.getMovieDetailLiveData().value?.data?.videos?.Trailers?.get(0)?.key
                                )
                    ).createChooserIntent()
                var flags =
                    Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                flags = flags or Intent.FLAG_ACTIVITY_NEW_DOCUMENT
                shareIntent.addFlags(flags)
                if (shareIntent.resolveActivity(packageManager) != null) {
                    startActivity(shareIntent)
                }
                true
            }
            R.id.action_favorite -> {
                movieDetailViewModel.onFavoriteClicked()
                invalidateOptionsMenu()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.movie_details, menu)
        UiUtils.tintMenuIcon(this, menu.findItem(R.id.action_share), R.color.text_light)
        val favoriteItem = menu.findItem(R.id.action_favorite)
        if (movieDetailViewModel.isFavorite()) {
            favoriteItem.setIcon(R.drawable.ic_favorite_black_24dp).setTitle(R.string.action_remove_from_favorites)
        } else {
            favoriteItem.setIcon(R.drawable.ic_favorite_border_black_24dp).setTitle(R.string.action_favorite)
        }
        UiUtils.tintMenuIcon(this, favoriteItem, R.color.text_light)
        return true
    }






}