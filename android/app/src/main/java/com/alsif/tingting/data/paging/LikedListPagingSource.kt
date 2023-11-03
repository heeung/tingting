package com.alsif.tingting.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.request.ConcertListRequestDto
import com.alsif.tingting.data.repository.HomeRepository
import com.alsif.tingting.data.repository.LikeRepository
import javax.inject.Inject

class LikedListPagingSource @Inject constructor (
    private val likeRepository: LikeRepository,
    private val userSeq: Int,
    private val itemCount: Int,
    private val throwError: (Exception) -> Unit
) : PagingSource<Int, ConcertDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ConcertDto> {
        val page = params.key ?: 1

        return try {
            val response = likeRepository.getLikedConcertList(userSeq, page, itemCount)
            val concerts = response.concerts
            LoadResult.Page(
                data = concerts,
                prevKey = null,
                nextKey = if (page < response.totalPage) page + 1 else null
            )
        } catch (exception: Exception) {
            throwError(exception)
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ConcertDto>): Int? {
        return state.anchorPosition
    }
}