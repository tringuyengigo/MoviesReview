package gdsvn.tringuyen.moviesreview.data.local.model.moviedetail

import com.google.gson.annotations.SerializedName

data class Reviews(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val reviews: List<Review>,
    @SerializedName("total_pages")
    val total_pages: Int,
    @SerializedName("total_results")
    val total_results: Int
)