package com.alsif.tingting.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.request.ConcertListRequestDto
import com.alsif.tingting.data.model.response.ConcertListResponseDto
import com.alsif.tingting.data.repository.HomeRepository
import com.alsif.tingting.data.repository.SearchRepository
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

private const val TAG = "SearchPagingSource"
class SearchPagingSource @Inject constructor (
    private val searchRepository: SearchRepository,
    private val concertListRequestDto: ConcertListRequestDto,
    private val throwError: (Exception) -> Unit,
) : PagingSource<Int, ConcertDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ConcertDto> {
        val page = params.key ?: 1

        return try {
            val response: ConcertListResponseDto =
                when {
                    // 검색어만
                    (concertListRequestDto.startDate == "" && concertListRequestDto.endDate == "" && concertListRequestDto.place == "") -> {
                        searchRepository.getConcertList(page, concertListRequestDto.itemCount, concertListRequestDto.searchWord)
                    }
                    // 장소 + 검색어
                    (concertListRequestDto.startDate == "" && concertListRequestDto.endDate == "") -> {
                        searchRepository.getConcertList(page, concertListRequestDto.itemCount, concertListRequestDto.place, concertListRequestDto.searchWord)
                    }
                    // 기간 + 검색어
                    (concertListRequestDto.place == "") -> {
                        searchRepository.getConcertList(page, concertListRequestDto.itemCount, concertListRequestDto.startDate, concertListRequestDto.endDate, concertListRequestDto.searchWord)
                    }
                    // 전부다
                    else -> {
                        searchRepository.getConcertList(page, concertListRequestDto.itemCount, concertListRequestDto.startDate, concertListRequestDto.endDate, concertListRequestDto.place, concertListRequestDto.searchWord)
                    }
                }
            val concerts = response.concerts
            LoadResult.Page(
                data = concerts,
                prevKey = null,
                nextKey = if (page < response.totalPage) page + 1 else null
            )
        } catch (exception: Exception) {
            Log.e(TAG, "load: network 연결 에러? -> ${exception}")
            throwError(exception)
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ConcertDto>): Int? {
        return state.anchorPosition
    }
}