package gdsvn.tringuyen.moviesreview.presentation.ui.activity.moviedetail.adapters.reviews

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import gdsvn.tringuyen.moviesreview.R
import gdsvn.tringuyen.moviesreview.data.local.model.moviedetail.Review
import kotlinx.android.synthetic.main.item_review.view.*


/**
 * @author Yassin Ajdi
 * @since 11/12/2018.
 */
class ReviewsViewHolder(private val root: View, context: Context) :
    RecyclerView.ViewHolder(root) {
    fun bindTo(review: Review) {
        val userName: String = review.author
        // review user image
        val generator: ColorGenerator = ColorGenerator.MATERIAL
        val color: Int = generator.randomColor
        val drawable: TextDrawable = TextDrawable.builder()
            .buildRound(userName.substring(0, 1).toUpperCase(), color)
        root.image_author.setImageDrawable(drawable)
        // review's author
        root.text_author.text = userName
        // review's content
        root.text_content.text = review.content
    }

    companion object {
        fun create(parent: ViewGroup): ReviewsViewHolder { // Inflate
            val layoutInflater = LayoutInflater.from(parent.context)
            return ReviewsViewHolder(layoutInflater.inflate( R.layout.item_review, parent, false), parent.context)
        }
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            root.frame.clipToOutline = false
        }
    }
}