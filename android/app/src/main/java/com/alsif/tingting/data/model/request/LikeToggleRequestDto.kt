package com.alsif.tingting.data.model.request


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class LikeToggleRequestDto(
    @SerializedName("concertSeq")
    val concertSeq: Int,
    @SerializedName("userSeq")
    val userSeq: Int
) : Parcelable