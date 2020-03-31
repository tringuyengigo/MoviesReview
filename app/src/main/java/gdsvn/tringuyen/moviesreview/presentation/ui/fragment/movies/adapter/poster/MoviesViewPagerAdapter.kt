package gdsvn.tringuyen.moviesreview.presentation.ui.fragment.movies.adapter.poster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import gdsvn.tringuyen.moviesreview.R
import gdsvn.tringuyen.moviesreview.data.local.model.movies.Movie
import gdsvn.tringuyen.moviesreview.presentation.common.define.Define
import kotlinx.android.synthetic.main.item_poster.view.*


class MoviesViewPagerAdapter : RecyclerView.Adapter<PagerViewHolder>() {
    var movies: List<Movie> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PagerViewHolder {
        return LayoutInflater.from(parent.context).inflate(
            PagerViewHolder.LAYOUT, parent, false
        ).let {
            PagerViewHolder(
                it
            )
        }
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(movies[position])
    }
}

class PagerViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
    fun bind(movie: Movie) {
        val posterURL = Define.URL_LOAD_IMAGE + movie!!.backdrop_path

        root.text_view_name_movie?.text = movie.title
        root.image_view_poster.let {
            Glide.with(it.context)
                .load(posterURL)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(it)
        }
    }

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.item_poster
    }
}