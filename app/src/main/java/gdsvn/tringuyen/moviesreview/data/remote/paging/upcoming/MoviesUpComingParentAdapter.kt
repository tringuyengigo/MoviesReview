package gdsvn.tringuyen.moviesreview.data.remote.paging.upcoming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gdsvn.tringuyen.moviesreview.R
import gdsvn.tringuyen.moviesreview.data.local.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesUpComingParentAdapter(view: View) : RecyclerView.ViewHolder(view) {

    private val imageLink = "https://image.tmdb.org/t/p/w500"
    fun bind(movie: Movie?) {
        if (movie != null) {
            itemView.txt_poster.text = movie.title
            Glide.with(itemView)
                .load(imageLink + movie.poster_path)
                .into(itemView.image_view_movie);
        }
    }

    companion object {
        fun create(parent: ViewGroup): MoviesUpComingParentAdapter {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
            return MoviesUpComingParentAdapter(
                view
            )
        }
    }
}
