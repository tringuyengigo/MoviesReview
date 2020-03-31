package gdsvn.tringuyen.moviesreview.presentation.ui.activity.moviedetail.adapters.trailer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gdsvn.tringuyen.moviesreview.R
import gdsvn.tringuyen.moviesreview.data.local.model.moviedetail.Trailer
import gdsvn.tringuyen.moviesreview.presentation.ui.activity.moviedetail.adapters.cast.CastViewHolder
import gdsvn.tringuyen.moviesreview.presentation.common.define.Define
import kotlinx.android.synthetic.main.item_trailer.view.*

class TrailerViewHolder(private val root: View, context: Context) :
    RecyclerView.ViewHolder(root) {
    private val context: Context = context

    fun bindTo(Trailer: Trailer) {
        val thumbnail =
            "https://img.youtube.com/vi/" + Trailer.key.toString() + "/hqdefault.jpg"
        Glide.with(context)
            .load(thumbnail)
            .placeholder(R.color.colorWhite)
            .into(root.image_trailer)

        root.trailer_name.text = Trailer.name
        root.card_trailer.setOnClickListener(View.OnClickListener {
            val appIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("vnd.youtube:" + Trailer.key)
            )
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(Define.YOUTUBE_WEB_URL + Trailer.key)
            )
            if (appIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(appIntent)
            } else {
                context.startActivity(webIntent)
            }
        })

    }

    companion object {
        fun create(parent: ViewGroup): TrailerViewHolder { // Inflate
            val layoutInflater = LayoutInflater.from(parent.context)
            return TrailerViewHolder(layoutInflater.inflate( R.layout.item_trailer, parent, false), parent.context)
        }
    }


}