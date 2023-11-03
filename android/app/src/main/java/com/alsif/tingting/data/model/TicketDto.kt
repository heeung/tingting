package com.alsif.tingting.data.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
@Parcelize
data class TicketDto(
    @SerializedName("concertHallCity")
    val concertHallCity: String,
    @SerializedName("concertHallName")
    val concertHallName: String,
    @SerializedName("concertSeq")
    val concertSeq: Int,
    @SerializedName("createdDate")
    val createdDate: String,
    @SerializedName("deletedDate")
    val deletedDate: String?,
    @SerializedName("holdDate")
    val holdDate: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("seats")
    val seats: List<SeatDto>,
    @SerializedName("ticketSeq")
    val ticketSeq: Long,
    @SerializedName("totalPrice")
    val totalPrice: Int,
    var isExpanded: Boolean
) : Parcelable {
    fun seatsInfo(): String {
        return this.seats.toString().replace("[", "").replace("]", "")
    }
    fun sectionInfo() : String {
        val sections = HashSet<String>()
        for (seat in seats) {
            sections.add(seat.section)
        }
        return sections.toString()
    }
}