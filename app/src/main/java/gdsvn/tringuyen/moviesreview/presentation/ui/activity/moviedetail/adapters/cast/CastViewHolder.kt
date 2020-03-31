package gdsvn.tringuyen.moviesreview.presentation.ui.activity.moviedetail.adapters.cast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gdsvn.tringuyen.moviesreview.R
import gdsvn.tringuyen.moviesreview.data.local.model.moviedetail.Cast
import gdsvn.tringuyen.moviesreview.presentation.common.define.Define
import kotlinx.android.synthetic.main.item_cast.view.*
import timber.log.Timber

/**
 * @author Yassin Ajdi
 * @since 11/12/2018.
 */
class CastViewHolder(private val root: View, context: Context) :
    RecyclerView.ViewHolder(root) {
    private val context: Context = context

    fun bindTo(cast: Cast) {

        Timber.e("Cast ======> ${cast}")
        val profileImage: String =
            Define.IMAGE_BASE_URL + Define.PROFILE_SIZE_W185 + cast.profile_path
        Glide.with(context)
            .load(profileImage)
            .placeholder(R.color.colorWhite)
            .dontAnimate()
            .into(root.image_cast_profile)
        root.text_cast_name.text = cast.name
    }

    companion object {
        fun create(parent: ViewGroup): CastViewHolder { // Inflate
            val layoutInflater = LayoutInflater.from(parent.context)
            return CastViewHolder(layoutInflater.inflate( R.layout.item_cast, parent, false), parent.context)
        }
    }

}