package com.alsif.tingting.data.repository

import com.alsif.tingting.data.model.request.LikeToggleRequestDto
import com.alsif.tingting.data.model.response.ConcertListResponseDto
import com.alsif.tingting.data.model.response.LikeToggleResponseDto
import com.alsif.tingting.data.service.HomeService
import com.alsif.tingting.data.service.LikeService
import com.alsif.tingting.util.handleApi
import javax.inject.Inject

class LikeRepositoryImpl @Inject constructor(
    private val likeService: LikeService
): LikeRepository {
    /**
     * GET 콘서트 리스트 (LikedList 페이지)
     */
    override suspend fun getLikedConcertList(
        userSeq: Int,
        currentPage: Int,
        itemCount: Int
    ): ConcertListResponseDto {
        return handleApi { likeService.getLikedConcertList(userSeq, currentPage, itemCount) }
    }

    /**
     * POST 찜 토글
     */
    override suspend fun postLike(likeToggleRequestDto: LikeToggleRequestDto): LikeToggleResponseDto {
        return handleApi { likeService.postLike(likeToggleRequestDto) }
    }

}