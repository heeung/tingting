package com.alsif.tingtinqg.data.service

import com.alsif.tingting.data.model.response.ReservedListResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReserveService {
    @GET("users/{userSeq}/ticket")
    suspend fun getReservedTicketList(
        @Path("userSeq") userSeq: Int,
        @Query("currentPage") currentPage: Int,
        @Query("itemCount") itemCount: Int
    ): Response<ReservedListResponseDto>
}