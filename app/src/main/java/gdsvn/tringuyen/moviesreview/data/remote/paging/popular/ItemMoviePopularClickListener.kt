package gdsvn.tringuyen.moviesreview.data.remote.paging.popular

import android.view.View

interface ItemMoviePopularClickListener {
    fun onItemMovieClick(view: View?, position: Int, isLongClick: Boolean)
}