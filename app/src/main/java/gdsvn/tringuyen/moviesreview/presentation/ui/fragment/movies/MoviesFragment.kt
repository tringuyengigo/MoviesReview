package gdsvn.tringuyen.moviesreview.presentation.ui.fragment.movies

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import gdsvn.tringuyen.moviesreview.R
import gdsvn.tringuyen.moviesreview.data.local.model.Movie
import gdsvn.tringuyen.moviesreview.data.remote.paging.common.State
import gdsvn.tringuyen.moviesreview.data.remote.paging.now.MoviesNowListAdapter
import gdsvn.tringuyen.moviesreview.data.remote.paging.top.MoviesTopListAdapter
import gdsvn.tringuyen.moviesreview.presentation.common.Define
import gdsvn.tringuyen.moviesreview.presentation.vm.popular.MoviesPopularViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.movies_fragment.*
import kotlinx.android.synthetic.main.movies_fragment_main_layout.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.concurrent.TimeUnit


class MoviesFragment : Fragment() {

    private val moviesPopularViewModel: MoviesPopularViewModel by viewModel()
    private lateinit var mMoviesListAdapter: MoviesTopListAdapter
    private lateinit var mMoviesNowListAdapter: MoviesNowListAdapter
    private lateinit var mMoviesTopListAdapter: MoviesTopListAdapter

    companion object {
        fun newInstance() = MoviesFragment()
    }

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movies_fragment_main_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    override fun onStart() {
        super.onStart()
        //set app is fullscreen
        this.activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        showListMovie(false)
        setupToolbar()
        initAdapter()
        initState()
    }

    private fun showListMovie(isShow: Boolean) {
        if(isShow) {
            textview_now.visibility =View.VISIBLE
            textview_popular.visibility =View.VISIBLE
            textview_top.visibility =View.VISIBLE
        } else {
            textview_now.visibility =View.GONE
            textview_popular.visibility =View.GONE
            textview_top.visibility =View.GONE
        }

    }

    private fun setupToolbar() {
        handleCollapsedToolbarTitle()
    }


    private fun initAdapter() {

        mMoviesListAdapter = MoviesTopListAdapter { moviesPopularViewModel.retry() }
        first_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        first_recycler_view.adapter = mMoviesListAdapter
        moviesPopularViewModel.movieList.observe(this, Observer {
            mMoviesListAdapter.submitList(it)
        })

        mMoviesNowListAdapter = MoviesNowListAdapter { moviesPopularViewModel.retry() }
        second_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        second_recycler_view.adapter = mMoviesNowListAdapter
        moviesPopularViewModel.movieList.observe(this, Observer { listMovie ->

            mMoviesNowListAdapter.submitList(listMovie)
            if(!listMovie.isEmpty()) {
                showPoster(listMovie)
                showListMovie(true)
            }

        })

        mMoviesTopListAdapter = MoviesTopListAdapter { moviesPopularViewModel.retry() }
        third_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        third_recycler_view.adapter = mMoviesTopListAdapter
        moviesPopularViewModel.movieList.observe(this, Observer {
            mMoviesTopListAdapter.submitList(it)
        })

    }

    private fun initState() {
        txt_error.setOnClickListener { moviesPopularViewModel.retry() }
        moviesPopularViewModel.getState().observe(this, Observer { state ->
            progress_bar.visibility = if (moviesPopularViewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (moviesPopularViewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (moviesPopularViewModel.listIsEmpty() && state == State.ERROR) {
                showListMovie(false)
            }
            if (!moviesPopularViewModel.listIsEmpty()) {
                mMoviesListAdapter.setState(state ?: State.DONE)

            }
        })
    }

    /**
     * Sets 20 poster on the imageView every 5(s) with animation. Maybe I will change imageView to Recycle view to load all image.
     */
    @SuppressLint("CheckResult")
    private fun showPoster(listMovie: PagedList<Movie>) {
        Timber.d("Start show poster ---------------------------------")
        Observable.interval(0,5, TimeUnit.SECONDS)
            .map { i -> listMovie[i.toInt()] }
            .take(listMovie.size.toLong())
            .repeatWhen { completed -> completed.delay(2, TimeUnit.SECONDS) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {movie ->
                val posterURL = Define.URL_LOAD_IMAGE + movie!!.backdrop_path
                val animFadeIn: Animation = AnimationUtils.loadAnimation(
                    this.context,
                    R.anim.fade_in
                )
                image_movie_backdrop.startAnimation(animFadeIn)

                Glide.with(this)
                    .load(posterURL)
                    .into(image_movie_backdrop);

            }
    }

    /**
     * Sets the title on the toolbar only if the toolbar is collapsed
     */
    private fun handleCollapsedToolbarTitle() {
        this.appbar.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                // verify if the toolbar is completely collapsed and set the movie name as the title
                if (scrollRange + verticalOffset == 0) {
                    this@MoviesFragment.collapsing_toolbar.title = this@MoviesFragment.getString(R.string.movies)
                    this@MoviesFragment.collapsing_toolbar.setCollapsedTitleTextColor(resources.getColor(R.color.text_light))
                    this@MoviesFragment.collapsing_toolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
                    isShow = true
                } else if (isShow) { // display an empty string when toolbar is expanded
                    this@MoviesFragment.collapsing_toolbar.title = this@MoviesFragment.getString(R.string.movies)
                    this@MoviesFragment.collapsing_toolbar.setExpandedTitleColor(resources.getColor(R.color.text_light))
                    this@MoviesFragment.collapsing_toolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
                    isShow = false
                }
            }
        })
    }

}