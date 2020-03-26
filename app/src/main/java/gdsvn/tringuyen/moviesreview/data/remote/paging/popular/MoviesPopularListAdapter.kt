package gdsvn.tringuyen.moviesreview.data.remote.paging.popular

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import gdsvn.tringuyen.moviesreview.data.local.model.movies.Movie
import gdsvn.tringuyen.moviesreview.data.remote.paging.common.ListFooterViewHolder
import gdsvn.tringuyen.moviesreview.data.remote.paging.common.State
import gdsvn.tringuyen.moviesreview.presentation.ui.activity.moviedetail.MovieDetailActivity
import timber.log.Timber


class MoviesPopularListAdapter(private val retry: () -> Unit) : PagedListAdapter<Movie, RecyclerView.ViewHolder>( NewsDiffCallback ) {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2

    private var state =
        State.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) MoviesPopularParentAdapter.create(parent) else ListFooterViewHolder.create(
            retry,
            parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE) {
            (holder as MoviesPopularParentAdapter).bind(getItem(position))
            holder.setItemClickListener(object : ItemMoviePopularClickListener {
                override fun onItemMovieClick(view: View?, position: Int, isLongClick: Boolean) {
                    Timber.e("onItemMovieClick ---> $position ---- ${Gson().toJson(getItem(position))}")
                    view?.context?.startActivity(Intent(view.context, MovieDetailActivity::class.java))
                }
            })
        }
        else (holder as ListFooterViewHolder).bind(state)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }
}