package com.alsif.tingting.data.model.request


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ReserveRequestDto(
    @SerializedName("seatSeqs")
    val seatSeqs: List<Long>
) : Parcelable