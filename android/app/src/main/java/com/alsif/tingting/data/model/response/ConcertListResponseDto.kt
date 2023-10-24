package com.alsif.tingting.data.model.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.alsif.tingting.data.model.ConcertDto

@Parcelize
data class ConcertListResponseDto(
    @SerializedName("concerts")
    val concerts: List<ConcertDto>,
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("totalPage")
    val totalPage: Int
) : Parcelable {
    constructor() : this(emptyList(), 0, 0)
}