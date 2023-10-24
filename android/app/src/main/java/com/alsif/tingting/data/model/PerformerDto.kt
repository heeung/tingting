package com.alsif.tingting.data.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class PerformerDto(
    @SerializedName("seq")
    val seq: Int,
    @SerializedName("performerName")
    val performerName: String,
    @SerializedName("performerImageUrl")
    val performerImageUrl: String
) : Parcelable