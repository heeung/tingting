package com.alsif.tingting.data.repository

import com.alsif.tingting.data.model.ConcertDetailDto
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.MoneyDto
import com.alsif.tingting.data.model.request.ConcertListRequestDto
import com.alsif.tingting.data.model.response.ConcertListResponseDto
import com.alsif.tingting.data.service.HomeService
import com.alsif.tingting.util.handleApi
import retrofit2.http.Query
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeService
) : HomeRepository {
    /**
     * GET 콘서트 리스트
     */
    override suspend fun getConcertList(concertListRequestDto: ConcertListRequestDto): ConcertListResponseDto {
        return handleApi {
            homeService.getConcertList(
                currentPage = concertListRequestDto.currentPage,
                itemCount = concertListRequestDto.itemCount,
                orderBy = concertListRequestDto.orderBy
            )
        }
    }

    /**
     * GET 콘서트 상세
     */
    override suspend fun getConcertDetail(concertSeq: Int, userSeq: Int): ConcertDetailDto {
        return handleApi { homeService.getConcertDetail(concertSeq, userSeq) }
    }

    override suspend fun getMyMoneyInfo(userSeq: Int): MoneyDto {
        return handleApi { homeService.getMyMoneyInfo(userSeq) }
    }
}