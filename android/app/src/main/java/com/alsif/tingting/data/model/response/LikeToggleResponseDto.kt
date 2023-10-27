package com.alsif.tingting.data.model.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class LikeToggleResponseDto(
    @SerializedName("favorite")
    val favorite: Boolean
) : Parcelable