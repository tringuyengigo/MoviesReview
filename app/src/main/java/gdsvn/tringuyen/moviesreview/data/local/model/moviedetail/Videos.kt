package gdsvn.tringuyen.moviesreview.data.local.model.moviedetail

import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("results")
    val Trailers: List<Trailer>
)