package com.alsif.tingting.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.request.ConcertListRequestDto
import com.alsif.tingting.data.repository.HomeRepository
import com.alsif.tingting.data.throwable.DataThrowable
import javax.inject.Inject

private const val TAG = "ConcertPagingSource"
class ConcertPagingSource @Inject constructor (
    private val homeRepository: HomeRepository,
    private val concertListRequestDto: ConcertListRequestDto,
    private val throwError: (Exception) -> Unit
) : PagingSource<Int, ConcertDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ConcertDto> {
        val page = params.key ?: 1

        return try {
            // TODO 나중에 다시 확인해보자,,
            concertListRequestDto.currentPage = page
            Log.d(TAG, "콘서트리스트 불러오기 페이징: ${page} 페이지")
            val response = homeRepository.getConcertList(concertListRequestDto)
            val concerts = response.concerts
            LoadResult.Page(
                data = concerts,
                prevKey = null,
                nextKey = if (page < response.totalPage) page + 1 else null
            )
        } catch (exception: Exception) {
            Log.e(TAG, "load: network 연결 에러", )
            throwError(exception)
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ConcertDto>): Int? {
        return state.anchorPosition
    }
}