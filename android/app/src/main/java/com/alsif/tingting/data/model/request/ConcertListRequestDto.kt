package com.alsif.tingting.data.model.request


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ConcertListRequestDto(
    @SerializedName("currentPage")
    var currentPage: Int,
    @SerializedName("itemCount")
    val itemCount: Int,
    @SerializedName("orderBy")
    val orderBy: String,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("endDate")
    val endDate: String,
    @SerializedName("place")
    val place: String,
    @SerializedName("searchWord")
    val searchWord: String
) : Parcelable