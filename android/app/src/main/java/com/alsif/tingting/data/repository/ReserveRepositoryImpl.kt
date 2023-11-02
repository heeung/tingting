package com.alsif.tingting.data.repository

import com.alsif.tingting.data.model.response.ReservedListResponseDto
import com.alsif.tingting.util.handleApi
import com.alsif.tingtinqg.data.service.ReserveService
import javax.inject.Inject

class ReserveRepositoryImpl @Inject constructor(
    private val reserveService: ReserveService
): ReserveRepository {
    /**
     * GET 예매 리스트
     */
    override suspend fun getReservedTicketList(
        userSeq: Int,
        currentPage: Int,
        itemCount: Int
    ): ReservedListResponseDto {
        return handleApi { reserveService.getReservedTicketList(userSeq, currentPage, itemCount) }
    }
}