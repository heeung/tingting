package com.alsif.tingting.data.repository

import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.request.ConcertListRequestDto
import com.alsif.tingting.data.model.response.ConcertListResponseDto

interface HomeRepository {
    suspend fun getConcertList(concertListRequestDto: ConcertListRequestDto): ConcertListResponseDto
}