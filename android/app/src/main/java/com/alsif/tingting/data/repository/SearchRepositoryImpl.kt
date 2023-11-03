package com.alsif.tingting.data.repository

import com.alsif.tingting.data.model.ConcertDetailDto
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.request.ConcertListRequestDto
import com.alsif.tingting.data.model.response.ConcertListResponseDto
import com.alsif.tingting.data.service.HomeService
import com.alsif.tingting.data.service.SearchService
import com.alsif.tingting.util.handleApi
import retrofit2.http.Query
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchService
) : SearchRepository {
    /**
     * GET 콘서트 리스트 (검색어만)
     */
    override suspend fun getConcertList(
        currentPage: Int,
        itemCount: Int,
        searchWord: String
    ): ConcertListResponseDto {
        return handleApi { searchService.getConcertList(currentPage, itemCount, searchWord) }
    }

    /**
     * GET 콘서트 리스트 (기간 + 검색어)
     */
    override suspend fun getConcertList(
        currentPage: Int,
        itemCount: Int,
        startDate: String,
        endDate: String,
        searchWord: String
    ): ConcertListResponseDto {
        return handleApi { searchService.getConcertList(currentPage, itemCount, startDate, endDate, searchWord) }
    }

    /**
     * GET 콘서트 리스트 (장소 + 검색어)
     */
    override suspend fun getConcertList(
        currentPage: Int,
        itemCount: Int,
        place: String,
        searchWord: String
    ): ConcertListResponseDto {
        return handleApi { searchService.getConcertList(currentPage, itemCount, place, searchWord) }
    }

    /**
     * GET 콘서트 리스트 (장소 + 기간 + 검색어)
     */
    override suspend fun getConcertList(
        currentPage: Int,
        itemCount: Int,
        startDate: String,
        endDate: String,
        place: String,
        searchWord: String
    ): ConcertListResponseDto {
        return handleApi { searchService.getConcertList(currentPage, itemCount, startDate, endDate, place, searchWord) }
    }
}