package gdsvn.tringuyen.moviesreview.data.remote.paging.top

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gdsvn.tringuyen.moviesreview.R
import gdsvn.tringuyen.moviesreview.data.local.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesTopParentAdapter(view: View) : RecyclerView.ViewHolder(view) {

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
        fun create(parent: ViewGroup): MoviesTopParentAdapter {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
            return MoviesTopParentAdapter(
                view
            )
        }
    }
}

//class MoviesChildAdapter(private val movie: Movie?) : RecyclerView.Adapter<MoviesChildAdapter.ViewHolder>() {
//    private val imageLink = "https://image.tmdb.org/t/p/w500"
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val v = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_movie, parent, false)
//        return ViewHolder(v)
//    }
//
//    override fun getItemCount(): Int {
//        return 1
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        if (movie != null) {
//            holder.name.text = movie?.title
//            Glide.with(holder.itemView)
//                .load(imageLink + movie.poster_path)
//                .into(holder.imageMovie)
//        }
//
//    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val name: TextView = itemView.findViewById(R.id.txt_name)
//        val imageMovie: ImageView = itemView.findViewById(R.id.image_view_movie)
//    }
//}


