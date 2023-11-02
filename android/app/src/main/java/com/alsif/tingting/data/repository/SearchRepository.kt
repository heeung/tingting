package com.alsif.tingting.data.repository

import com.alsif.tingting.data.model.ConcertDetailDto
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.request.ConcertListRequestDto
import com.alsif.tingting.data.model.response.ConcertListResponseDto
import retrofit2.http.Query

interface SearchRepository {
    // 아무 것도 없이 검색어만
    suspend fun getConcertList(
        currentPage: Int,
        itemCount: Int,
        searchWord: String,
    ): ConcertListResponseDto

    // 기간 + 검색어
    suspend fun getConcertList(
        currentPage: Int,
        itemCount: Int,
        startDate: String,
        endDate: String,
        searchWord: String
    ): ConcertListResponseDto

    // 장소 + 검색어
    suspend fun getConcertList(
        currentPage: Int,
        itemCount: Int,
        place: String,
        searchWord: String,
    ): ConcertListResponseDto

    // 장소 + 기간 + 검색어
    suspend fun getConcertList(
        currentPage: Int,
        itemCount: Int,
        startDate: String,
        endDate: String,
        place: String,
        searchWord: String
    ): ConcertListResponseDto
}