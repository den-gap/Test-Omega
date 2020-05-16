package ru.dengap.testomega.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("wrapperType")
    @Expose
    var wrapperType: String? = null,

    @SerializedName("collectionType")
    @Expose
    var collectionType: String? = null,

    @SerializedName("artistId")
    @Expose
    var artistId: Int? = null,

    @SerializedName("collectionId")
    @Expose
    var collectionId: Int? = null,

    @SerializedName("amgArtistId")
    @Expose
    var amgArtistId: Int? = null,

    @SerializedName("artistName")
    @Expose
    var artistName: String? = null,

    @SerializedName("collectionName")
    @Expose
    var collectionName: String? = null,

    @SerializedName("collectionCensoredName")
    @Expose
    var collectionCensoredName: String? = null,

    @SerializedName("artistViewUrl")
    @Expose
    var artistViewUrl: String? = null,

    @SerializedName("collectionViewUrl")
    @Expose
    var collectionViewUrl: String? = null,

    @SerializedName("artworkUrl60")
    @Expose
    var artworkUrl60: String? = null,

    @SerializedName("artworkUrl100")
    @Expose
    var artworkUrl100: String? = null,

    @SerializedName("collectionPrice")
    @Expose
    private var collectionPrice: Double? = null,

    @SerializedName("collectionExplicitness")
    @Expose
    var collectionExplicitness: String? = null,

    @SerializedName("trackCount")
    @Expose
    var trackCount: Int? = null,

    @SerializedName("copyright")
    @Expose
    var copyright: String? = null,

    @SerializedName("country")
    @Expose
    var country: String? = null,

    @SerializedName("currency")
    @Expose
    var currency: String? = null,

    @SerializedName("releaseDate")
    @Expose
    var releaseDate: String? = null,

    @SerializedName("primaryGenreName")
    @Expose
    var primaryGenreName: String? = null,

    @SerializedName("contentAdvisoryRating")
    @Expose
    var contentAdvisoryRating: String? = null
) : Result