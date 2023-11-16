package com.alsif.tingting.data.model.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class MessageResponseDto(
    @SerializedName("message")
    val message: String
) : Parcelable