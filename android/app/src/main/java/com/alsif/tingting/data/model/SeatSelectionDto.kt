package com.alsif.tingting.data.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class SeatSelectionDto(
    @SerializedName("book")
    var book: Boolean,
    @SerializedName("concertSeatInfoSeq")
    val concertSeatInfoSeq: Long,
    @SerializedName("grade")
    val grade: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("seat")
    val seat: String,
    @SerializedName("section")
    val section: String
) : Parcelable, Comparable<SeatSelectionDto> {
    override fun compareTo(other: SeatSelectionDto): Int {
        if (this.concertSeatInfoSeq - other.concertSeatInfoSeq > 0L)
            return -1
        else if (this.concertSeatInfoSeq - other.concertSeatInfoSeq == 0L)
            return 0
        else
            return 1
    }
}