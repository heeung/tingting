package com.alsif.tingting.data.service

import com.alsif.tingting.data.model.ConcertDetailDto
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.request.ConcertListRequestDto
import com.alsif.tingting.data.model.response.ConcertListResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
interface SearchService {

    // 아무 것도 없이 검색어만
    @GET("concerts")
    suspend fun getConcertList(
        @Query("currentPage") currentPage: Int,
        @Query("itemCount") itemCount: Int,
        @Query("searchWord") searchWord: String,
    ): Response<ConcertListResponseDto>

    // 기간 + 검색어
    @GET("concerts")
    suspend fun getConcertList(
        @Query("currentPage") currentPage: Int,
        @Query("itemCount") itemCount: Int,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("searchWord") searchWord: String
    ): Response<ConcertListResponseDto>

    // 장소 + 검색어
    @GET("concerts")
    suspend fun getConcertList(
        @Query("currentPage") currentPage: Int,
        @Query("itemCount") itemCount: Int,
        @Query("place") place: String,
        @Query("searchWord") searchWord: String,
    ): Response<ConcertListResponseDto>

    // 장소 + 기간 + 검색어
    @GET("concerts")
    suspend fun getConcertList(
        @Query("currentPage") currentPage: Int,
        @Query("itemCount") itemCount: Int,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("place") place: String,
        @Query("searchWord") searchWord: String
    ): Response<ConcertListResponseDto>
}