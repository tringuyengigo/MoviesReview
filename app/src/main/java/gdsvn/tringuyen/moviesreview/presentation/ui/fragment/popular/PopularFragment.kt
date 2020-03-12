package gdsvn.tringuyen.moviesreview.presentation.ui.fragment.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.google.gson.Gson
import gdsvn.tringuyen.moviesreview.R
import gdsvn.tringuyen.moviesreview.presentation.common.Status
import gdsvn.tringuyen.moviesreview.presentation.vm.popular.MoviesPopularViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class PopularFragment : Fragment() {

    private val moviesPopularViewModel: MoviesPopularViewModel by viewModel()

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

        moviesPopularViewModel.movieList.observe(this, Observer {
                data -> Timber.e("moviesPopularViewModel Loading data........... data :  ${data.size}")

        })


        moviesPopularViewModel.getMoviesPopularLiveData().observe(this, Observer { data ->
            when (data?.responseType) {
                Status.ERROR -> {
                    Timber.v("Loading data........... ERROR ${data.error}")
                }
                Status.LOADING -> {
                    Timber.v("Loading data........... LOADING")
                }
                Status.SUCCESSFUL -> {
                    Timber.v("Loading data........... SUCCESSFUL")
                }
            }
            data?.data?.let { movies ->
                Timber.v("Data Movies at PopularFragment movies size ${movies.results.size}")
                Timber.v("Data Movies at PopularFragment ${Gson().toJson(movies)}")
                moviesPopularViewModel.retry()
            }
        })
    }



}