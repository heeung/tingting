package com.alsif.tingting.data.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ConcertDetailDto(
    @SerializedName("bookCloseDate")
    val bookCloseDate: String,
    @SerializedName("bookOpenDate")
    val bookOpenDate: String,
    @SerializedName("concertDetails")
    val concertDetails: List<ConcertScheduleDto>,
    @SerializedName("concertHallCity")
    val concertHallCity: String,
    @SerializedName("concertHallName")
    val concertHallName: String,
    @SerializedName("concertSeq")
    val concertSeq: Int,
    @SerializedName("favorite")
    val favorite: Boolean,
    @SerializedName("holdCloseDate")
    var holdCloseDate: String,
    @SerializedName("holdOpenDate")
    var holdOpenDate: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("info")
    val info: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("performers")
    val performers: List<PerformerDto>
) : Parcelable {
    constructor(): this("", "", emptyList(), "", "", 0, false, "", "", "", "" ,"" , emptyList())
}