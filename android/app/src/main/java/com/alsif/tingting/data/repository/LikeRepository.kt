package com.alsif.tingting.data.repository

import com.alsif.tingting.data.model.response.ConcertListResponseDto

interface LikeRepository {
    suspend fun getLikedConcertList(userSeq: Int, currentPage: Int, itemCount: Int): ConcertListResponseDto
}