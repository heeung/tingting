package com.alsif.tingting.data.service

import com.alsif.tingting.data.model.request.LikeToggleRequestDto
import com.alsif.tingting.data.model.response.ConcertListResponseDto
import com.alsif.tingting.data.model.response.LikeToggleResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface LikeService {
    @GET("users/{userSeq}/favorite")
    suspend fun getLikedConcertList(
        @Path("userSeq") userSeq: Int,
        @Query("currentPage") currentPage: Int,
        @Query("itemCount") itemCount: Int
    ): Response<ConcertListResponseDto>

    @POST("concerts/favorite")
    suspend fun postLike(
        @Body likeToggleRequestDto: LikeToggleRequestDto
    ): Response<LikeToggleResponseDto>
}