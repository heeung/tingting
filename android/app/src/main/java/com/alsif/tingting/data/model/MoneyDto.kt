package com.alsif.tingting.data.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class MoneyDto(
    @SerializedName("point")
    val point: Int
) : Parcelable