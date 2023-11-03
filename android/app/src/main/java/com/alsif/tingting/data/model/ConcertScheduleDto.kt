package com.alsif.tingting.data.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ConcertScheduleDto(
    @SerializedName("seq")
    val seq: Int,
    @SerializedName("concertSeq")
    val concertSeq: Int,
    @SerializedName("holdDate")
    val holdDate: String
) : Parcelable