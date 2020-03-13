package gdsvn.tringuyen.moviesreview.presentation.ui.fragment.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gdsvn.tringuyen.moviesreview.R
import gdsvn.tringuyen.moviesreview.data.remote.paging.MoviesListAdapter
import gdsvn.tringuyen.moviesreview.data.remote.paging.State
import gdsvn.tringuyen.moviesreview.presentation.vm.popular.MoviesPopularViewModel
import kotlinx.android.synthetic.main.popular_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class PopularFragment : Fragment() {

    private val moviesPopularViewModel: MoviesPopularViewModel by viewModel()
    private lateinit var mMoviesListAdapter: MoviesListAdapter
    companion object {
        fun newInstance() = PopularFragment()
    }

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.popular_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = this!!.getString(R.string.app_name)
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = this!!.getString(R.string.popular)
    }

    override fun onStart() {
        super.onStart()
        initAdapter()
        initState()
    }

    private fun initAdapter() {
        mMoviesListAdapter = MoviesListAdapter { moviesPopularViewModel.retry() }
        recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recycler_view.adapter = mMoviesListAdapter

        moviesPopularViewModel.movieList.observe(this, Observer {
            mMoviesListAdapter.submitList(it)
        })
    }

    private fun initState() {
        txt_error.setOnClickListener { moviesPopularViewModel.retry() }
        moviesPopularViewModel.getState().observe(this, Observer { state ->
            progress_bar.visibility = if (moviesPopularViewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (moviesPopularViewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!moviesPopularViewModel.listIsEmpty()) {
                mMoviesListAdapter.setState(state ?: State.DONE)

            }
        })
    }



}