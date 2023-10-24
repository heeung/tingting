package com.alsif.tingting.data.repository

import com.alsif.tingting.data.model.ConcertDto
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
                orderBy = concertListRequestDto.orderBy,
                startDate = concertListRequestDto.startDate,
                endDate = concertListRequestDto.endDate,
                place = concertListRequestDto.place,
                searchWord = concertListRequestDto.searchWord,
            )
        }
    }
}