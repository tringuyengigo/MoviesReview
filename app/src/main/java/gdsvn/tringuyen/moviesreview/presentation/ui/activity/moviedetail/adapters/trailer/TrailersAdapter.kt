package gdsvn.tringuyen.moviesreview.presentation.ui.activity.moviedetail.adapters.trailer

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gdsvn.tringuyen.moviesreview.data.local.model.moviedetail.Trailer

/**
 * @author Yassin Ajdi.
 */
class TrailersAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var trailerList: List<Trailer>? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return TrailerViewHolder.Companion.create(parent)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val Trailer: Trailer = trailerList!![position]
        (holder as TrailerViewHolder).bindTo(Trailer)
    }

    override fun getItemCount(): Int {
        return if (trailerList != null) trailerList!!.size else 0
    }

    fun submitList(Trailers: List<Trailer>?) {
        trailerList = Trailers
        notifyDataSetChanged()
    }
}