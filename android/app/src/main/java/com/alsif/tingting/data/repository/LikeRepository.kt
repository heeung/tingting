package com.alsif.tingting.data.repository

import com.alsif.tingting.data.model.request.LikeToggleRequestDto
import com.alsif.tingting.data.model.response.ConcertListResponseDto
import com.alsif.tingting.data.model.response.LikeToggleResponseDto

interface LikeRepository {
    suspend fun getLikedConcertList(userSeq: Int, currentPage: Int, itemCount: Int): ConcertListResponseDto

    suspend fun postLike(likeToggleRequestDto: LikeToggleRequestDto): LikeToggleResponseDto
}