package gdsvn.tringuyen.moviesreview.presentation.ui.activity.moviedetail.adapters.reviews

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gdsvn.tringuyen.moviesreview.data.local.model.moviedetail.Review
import gdsvn.tringuyen.moviesreview.data.local.model.moviedetail.Reviews

/**
 * @author Yassin Ajdi
 * @since 11/12/2018.
 */
class ReviewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var reviewList: List<Review>? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return ReviewsViewHolder.Companion.create(parent)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val review: Review = reviewList!![position]
        (holder as ReviewsViewHolder).bindTo(review)
    }

    override fun getItemCount(): Int {
        return if (reviewList != null) reviewList!!.size else 0
    }

    fun submitList(reviews: List<Review>?) {
        reviewList = reviews
        notifyDataSetChanged()
    }
}