package gdsvn.tringuyen.moviesreview.data.remote.paging.now

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import gdsvn.tringuyen.moviesreview.R
import gdsvn.tringuyen.moviesreview.data.local.model.movies.Movie
import gdsvn.tringuyen.moviesreview.presentation.common.define.Define
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesNowParentAdapter(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(movie: Movie?) {
        if (movie != null) {
            val posterURL = Define.URL_LOAD_IMAGE + movie.poster_path
            itemView.txt_poster.text = movie.title
            Glide.with(itemView)
                .load(posterURL)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(itemView.image_view_movie)
        }
    }

    companion object {
        fun create(parent: ViewGroup): MoviesNowParentAdapter {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
            return MoviesNowParentAdapter(
                view
            )
        }
    }
}
