package com.alsif.tingting.data.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ConcertDto(
    @SerializedName("concertHallCity")
    val concertHallCity: String,
    @SerializedName("concertHallName")
    val concertHallName: String,
    @SerializedName("concertSeq")
    val concertSeq: Int,
    @SerializedName("holdCloseDate")
    val holdCloseDate: String,
    @SerializedName("holdOpenDate")
    val holdOpenDate: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("name")
    val name: String
) : Parcelable