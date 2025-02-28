package gdsvn.tringuyen.moviesreview.presentation.ui.fragment.movies

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import gdsvn.tringuyen.moviesreview.R
import gdsvn.tringuyen.moviesreview.data.local.model.movies.Movie
import gdsvn.tringuyen.moviesreview.data.remote.paging.common.State
import gdsvn.tringuyen.moviesreview.data.remote.paging.now.MoviesNowListAdapter
import gdsvn.tringuyen.moviesreview.data.remote.paging.popular.MoviesPopularListAdapter
import gdsvn.tringuyen.moviesreview.data.remote.paging.top.MoviesTopListAdapter
import gdsvn.tringuyen.moviesreview.data.remote.paging.upcoming.MoviesUpComingListAdapter
import gdsvn.tringuyen.moviesreview.presentation.ui.fragment.movies.adapter.poster.MoviesViewPagerAdapter
import gdsvn.tringuyen.moviesreview.presentation.vm.popular.MoviesPopularViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.item_poster.view.*
import kotlinx.android.synthetic.main.movies_fragment.*
import kotlinx.android.synthetic.main.movies_fragment_main_layout.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.concurrent.TimeUnit
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import gdsvn.tringuyen.moviesreview.presentation.ui.activity.splash.SplashScreen

class MoviesFragment : Fragment() {

    private val moviesPopularViewModel: MoviesPopularViewModel by viewModel()

    private lateinit var mMoviesUpComingListAdapter: MoviesUpComingListAdapter
    private lateinit var mMoviesPopularListAdapter: MoviesPopularListAdapter
    private lateinit var mMoviesNowListAdapter: MoviesNowListAdapter
    private lateinit var mMoviesTopListAdapter: MoviesTopListAdapter

    private lateinit var moviesAdapter: MoviesViewPagerAdapter
    private lateinit var drawer: DrawerLayout
    private lateinit var navView: NavigationView



    override fun onCreateView (
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movies_fragment_main_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
        initMenuToolbar()
    }

    private fun initMenuToolbar() {
        toolbar.inflateMenu(R.menu.menu_icon)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_user -> {
                    openDrawer()
                }
            }
            true
        }

        handleEventsUserDrawer()
    }

    private fun handleEventsUserDrawer() {
        // Initialize the action bar drawer toggle instance
        val drawerToggle:ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            activity,
            drawer,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ){
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                initUserDrawer()
            }
        }

        // Configure the drawer layout to add listener and show icon on toolbar
        drawerToggle.isDrawerIndicatorEnabled = true
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        navView.setNavigationItemSelectedListener{
            when (it.itemId){
                R.id.nav_action_log_out -> {
                    signOut()
                }
            }
            // Close the drawer
            drawer.closeDrawer(GravityCompat.START)
            true
        }

    }

    private fun signOut(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent( this.activity , SplashScreen::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        showTitleListMovie(false)
        processToolbar()
        initAdapter()
        initState()
    }

    private fun initUI() {
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
        this.activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        drawer = activity!!.findViewById<View>(R.id.drawer_layout) as DrawerLayout
        navView = activity!!.findViewById<View>(R.id.nav_view) as NavigationView

    }

    private fun showTitleListMovie(isShow: Boolean) {
        if(isShow) {
            textview_now.visibility = View.VISIBLE
            textview_popular.visibility = View.VISIBLE
            textview_top.visibility = View.VISIBLE
            textview_up_coming.visibility = View.VISIBLE
        } else {
            textview_now.visibility = View.GONE
            textview_popular.visibility = View.GONE
            textview_top.visibility = View.GONE
            textview_up_coming.visibility = View.GONE
        }
    }

    private fun processToolbar() {
        handleCollapsedToolbarTitle()
    }

    private fun initAdapter() {

        mMoviesUpComingListAdapter = MoviesUpComingListAdapter { moviesPopularViewModel.retry() }
        up_comming_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        up_comming_recycler_view.adapter = mMoviesUpComingListAdapter

        mMoviesPopularListAdapter = MoviesPopularListAdapter { moviesPopularViewModel.retry() }
        popular_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        popular_recycler_view.adapter = mMoviesPopularListAdapter

        mMoviesNowListAdapter = MoviesNowListAdapter { moviesPopularViewModel.retry() }
        now_playing_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        now_playing_recycler_view.adapter = mMoviesNowListAdapter

        mMoviesTopListAdapter = MoviesTopListAdapter { moviesPopularViewModel.retry() }
        top_rate_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        top_rate_recycler_view.adapter = mMoviesTopListAdapter

        moviesAdapter =
            MoviesViewPagerAdapter()

        moviesPopularViewModel.movieList.observe(this, Observer { listMovie ->
            mMoviesUpComingListAdapter.submitList(listMovie)
            mMoviesPopularListAdapter.submitList(listMovie)
            mMoviesNowListAdapter.submitList(listMovie)
            mMoviesTopListAdapter.submitList(listMovie)

            if(!listMovie.isEmpty()) {
                showPoster(listMovie)
                moviesAdapter.movies = listMovie
                showTitleListMovie(true)
            }
        })


        with(view_paper_poster) {
            adapter = moviesAdapter
            setPageTransformer { page, position ->
                setParallaxTransformation(page, position)
            }
        }
    }


    private fun initState() {
        txt_error.setOnClickListener { moviesPopularViewModel.retry() }
        moviesPopularViewModel.getState().observe(this, Observer { state ->

            progress_bar.visibility = if (moviesPopularViewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (moviesPopularViewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE

            if (moviesPopularViewModel.listIsEmpty() && state == State.ERROR) { showTitleListMovie(false) }

            if (!moviesPopularViewModel.listIsEmpty()) {
                mMoviesPopularListAdapter.setState(state ?: State.DONE)
            }
        })
    }

    /**
     * Sets 20 poster on the imageView every 5(s) with animation.
     */
    @SuppressLint("CheckResult")
    private fun showPoster(listMovie: PagedList<Movie>) {
        Timber.d("Start show poster ---------------------------------")
        val list = IntRange(0, listMovie.size).toList()
        Observable.interval(0,5, TimeUnit.SECONDS)
            .map { i -> list[i.toInt()] }
            .take(list.size.toLong())
            .repeatWhen { completed -> completed.delay(2, TimeUnit.SECONDS) }
            .doOnError {error ->
                Timber.e("showPoster error ${error.message}")
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { currentItem ->
                    this.view_paper_poster.currentItem = currentItem
                }, { throwable -> Timber.e("showPoster ---> error ${throwable.message} ")}
            )

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
                    toolbar.setBackgroundColor(resources.getColor(R.color.classic_darkTheme_colorBackground))
                    this@MoviesFragment.collapsing_toolbar.setCollapsedTitleTextColor(resources.getColor(R.color.color_text_title))
                    isShow = true
                } else if (isShow) {
                    // display an empty string when toolbar is expanded
                    toolbar.setBackgroundColor(resources.getColor(R.color.colorBackground))
                    this@MoviesFragment.collapsing_toolbar.title = ""
                    this@MoviesFragment.collapsing_toolbar.setExpandedTitleColor(resources.getColor(R.color.color_text_title))
                    isShow = false
                }
            }
        })
    }

    private fun openDrawer() {
        drawer.openDrawer(GravityCompat.START)
        initUserDrawer()
    }

    private fun initUserDrawer() {
        context?.let {
            activity?.nav_header_user_image_view?.let { userImageView ->
                Glide.with(it)
                    .load("https://png.pngtree.com/png-clipart/20190924/original/pngtree-business-people-avatar-icon-user-profile-free-vector-png-image_4815126.jpg")
                    .apply(
                        RequestOptions
                            .circleCropTransform()
                            .error(R.drawable.ic_user))
                    .into(userImageView)
            }
            activity?.nav_header_user_name?.let { userName ->
                userName.text = "Nguyen Minh Tri"
            }
            displayBitmap("0939421821")
        }
    }


    private fun displayBitmap(value: String) {
        val widthPixels = resources.getDimensionPixelSize(R.dimen.width_barcode)
        val heightPixels = resources.getDimensionPixelSize(R.dimen.height_barcode)

        context?.let {
            activity?.nav_header_barcode?.setImageBitmap(
                moviesPopularViewModel.processBarcodeUser(
                    value,
                    widthPixels,
                    heightPixels,
                    resources.getColor(R.color.colorBlack),
                    resources.getColor(R.color.colorWhite)
                )
            )
            activity?.text_barcode_number?.text  = value
        }
    }

    private fun setParallaxTransformation(page: View, position: Float) {
        page.apply {
            val parallaxView = this.image_view_poster
            when {
                position < -1 -> // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    alpha = 1f
                position <= 1 -> { // [-1,1]
                    parallaxView.translationX = -position * (width / 2) //Half the normal speed
                }
                else -> // (1,+Infinity]
                    // This page is way off-screen to the right.
                    alpha = 1f
            }
        }
    }
}