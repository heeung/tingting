package com.alsif.tingting.data.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ConcertHallInfoDto(
    @SerializedName("concertHallSeq")
    val concertHallSeq: Int,
    @SerializedName("pattern")
    val pattern: String
) : Parcelable {
    constructor(): this(0, "A")
}