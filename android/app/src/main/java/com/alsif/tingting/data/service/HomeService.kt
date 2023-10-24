package com.alsif.tingting.data.service

import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.request.ConcertListRequestDto
import com.alsif.tingting.data.model.response.ConcertListResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
interface HomeService {
    @GET("concerts")
    suspend fun getConcertList(
        @Query("currentPage") currentPage: Int,
        @Query("itemCount") itemCount: Int,
        @Query("orderBy") orderBy: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("place") place: String,
        @Query("searchWord") searchWord: String,
    ): Response<ConcertListResponseDto>
}