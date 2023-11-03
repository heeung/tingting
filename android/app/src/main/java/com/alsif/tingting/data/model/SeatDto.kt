package com.alsif.tingting.data.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class SeatDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("seat")
    val seat: String,
    @SerializedName("section")
    val section: String,
    @SerializedName("ticketSeq")
    val ticketSeq: Long
) : Parcelable {
    override fun toString(): String {
        return "$section-$seat"
    }
}