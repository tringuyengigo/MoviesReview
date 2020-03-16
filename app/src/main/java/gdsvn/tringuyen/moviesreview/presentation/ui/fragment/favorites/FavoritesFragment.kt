package gdsvn.tringuyen.moviesreview.presentation.ui.fragment.favorites

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import gdsvn.tringuyen.moviesreview.R
import gdsvn.tringuyen.moviesreview.presentation.vm.top.TopRatedViewModel

class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance() =
            FavoritesFragment()
    }

    private lateinit var viewModel: TopRatedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TopRatedViewModel::class.java)
        (activity as? AppCompatActivity)?.supportActionBar?.title = this!!.getString(R.string.app_name)
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = this!!.getString(R.string.favorites)
    }



}