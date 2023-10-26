package com.alsif.tingting.data.service

import com.alsif.tingting.data.model.response.ConcertListResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LikeService {
    @GET("users/{userSeq}/favorite")
    suspend fun getLikedConcertList(
        @Path("userSeq") userSeq: Int,
        @Query("currentPage") currentPage: Int,
        @Query("itemCount") itemCount: Int
    ): Response<ConcertListResponseDto>
}