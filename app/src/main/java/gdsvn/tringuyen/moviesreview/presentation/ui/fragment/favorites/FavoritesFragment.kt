package gdsvn.tringuyen.moviesreview.presentation.ui.fragment.favorites

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import gdsvn.tringuyen.moviesreview.R
import gdsvn.tringuyen.moviesreview.presentation.vm.top.TopRatedViewModel
import kotlinx.android.synthetic.main.movies_fragment_main_layout.*

class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private lateinit var viewModel: TopRatedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorites_fragment_main_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()

    }

    private fun setupToolbar() {
        handleCollapsedToolbarTitle()
    }

    /**
     * sets the title on the toolbar only if the toolbar is collapsed
     */
    private fun handleCollapsedToolbarTitle() {
        this.appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                // verify if the toolbar is completely collapsed and set the movie name as the title
                if (scrollRange + verticalOffset == 0) {
                    this@FavoritesFragment.collapsing_toolbar.title = this@FavoritesFragment.getString(R.string.favorites)
                    isShow = true
                } else if (isShow) { // display an empty string when toolbar is expanded
                    this@FavoritesFragment.collapsing_toolbar.title = ""
                    isShow = false
                }
            }
        })
    }


}