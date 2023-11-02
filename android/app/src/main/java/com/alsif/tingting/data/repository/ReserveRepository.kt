package com.alsif.tingting.data.repository

import com.alsif.tingting.data.model.response.ReservedListResponseDto

interface ReserveRepository {
    suspend fun getReservedTicketList(userSeq: Int, currentPage: Int, itemCount: Int): ReservedListResponseDto
}