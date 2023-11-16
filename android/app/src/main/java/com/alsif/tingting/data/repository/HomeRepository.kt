package com.alsif.tingting.data.repository

import com.alsif.tingting.data.model.ConcertDetailDto
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.MoneyDto
import com.alsif.tingting.data.model.request.ConcertListRequestDto
import com.alsif.tingting.data.model.response.ConcertListResponseDto
import retrofit2.Response
import retrofit2.http.Path

interface HomeRepository {
    suspend fun getConcertList(concertListRequestDto: ConcertListRequestDto): ConcertListResponseDto

    suspend fun getConcertDetail(concertSeq: Int, userSeq: Int): ConcertDetailDto

    suspend fun getMyMoneyInfo(userSeq: Int): MoneyDto
}