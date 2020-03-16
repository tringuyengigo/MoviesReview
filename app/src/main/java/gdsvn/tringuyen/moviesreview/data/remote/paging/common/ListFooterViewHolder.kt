package gdsvn.tringuyen.moviesreview.data.remote.paging.common

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gdsvn.tringuyen.moviesreview.R
import kotlinx.android.synthetic.main.item_list_movie.view.*

class ListFooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(status: State?) {
        itemView.progress_bar.visibility = if (status == State.LOADING) VISIBLE else View.INVISIBLE
        itemView.txt_error.visibility = if (status == State.ERROR) VISIBLE else View.INVISIBLE
    }

    companion object {
        fun create(retry: () -> Unit, parent: ViewGroup): ListFooterViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_movie, parent, false)
            view.txt_error.setOnClickListener { retry() }
            return ListFooterViewHolder(
                view
            )
        }
    }
}