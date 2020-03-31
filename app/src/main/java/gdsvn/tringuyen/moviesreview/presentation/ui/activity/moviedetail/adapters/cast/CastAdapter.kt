package gdsvn.tringuyen.moviesreview.presentation.ui.activity.moviedetail.adapters.cast

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gdsvn.tringuyen.moviesreview.data.local.model.moviedetail.Cast

/**
 * @author Yassin Ajdi
 * @since 11/12/2018.
 */
class CastAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var castList: List<Cast>? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return CastViewHolder.create(parent)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val cast: Cast = castList!![position]
        (holder as CastViewHolder).bindTo(cast)
    }

    override fun getItemCount(): Int {
        return if (castList != null) castList!!.size else 0
    }

    fun submitList(casts: List<Cast>?) {
        castList = casts
        notifyDataSetChanged()
    }
}