package com.alsif.tingting.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.TicketDto
import com.alsif.tingting.data.model.request.ConcertListRequestDto
import com.alsif.tingting.data.repository.HomeRepository
import com.alsif.tingting.data.repository.LikeRepository
import com.alsif.tingting.data.repository.ReserveRepository
import javax.inject.Inject

class ReservedListPagingSource @Inject constructor (
    private val reserveRepository: ReserveRepository,
    private val userSeq: Int,
    private val itemCount: Int,
    private val throwError: (Exception) -> Unit
) : PagingSource<Int, TicketDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TicketDto> {
        val page = params.key ?: 1

        return try {
            val response = reserveRepository.getReservedTicketList(userSeq, page, itemCount)
            val tickets = response.tickets
            LoadResult.Page(
                data = tickets,
                prevKey = null,
                nextKey = if (page < response.totalPage) page + 1 else null
            )
        } catch (exception: Exception) {
            throwError(exception)
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TicketDto>): Int? {
        return state.anchorPosition
    }
}