package com.alsif.tingting.data.model.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.alsif.tingting.data.model.TicketDto

@Parcelize
data class ReservedListResponseDto(
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("tickets")
    val tickets: List<TicketDto>,
    @SerializedName("totalPage")
    val totalPage: Int
) : Parcelable