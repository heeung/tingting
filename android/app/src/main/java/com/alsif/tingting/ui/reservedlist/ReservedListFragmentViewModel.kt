package com.alsif.tingting.ui.reservedlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.TicketDto
import com.alsif.tingting.data.paging.LikedListPagingSource
import com.alsif.tingting.data.paging.ReservedListPagingSource
import com.alsif.tingting.data.repository.ReserveRepository
import com.alsif.tingting.data.throwable.DataThrowable
import com.alsif.tingting.ui.likedlist.LikedListFragmentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

private const val TAG = "ReservedListFragmentVie"
@HiltViewModel
class ReservedListFragmentViewModel @Inject constructor(
    private val reserveRepository: ReserveRepository
)  : ViewModel() {
    var isFirstRander = true

    // TODO api연결
    private val _refreshEvent = MutableSharedFlow<Boolean>()
    val refreshEvent = _refreshEvent.asSharedFlow()

    private val _reservedListPagingDataFlow = MutableStateFlow<PagingData<TicketDto>>(PagingData.empty())
    val reservedListPagingDataFlow = _reservedListPagingDataFlow.asStateFlow()

    private val _error = MutableSharedFlow<DataThrowable>()
    var error = _error.asSharedFlow()

    /*
    예매 리스트
     */
    fun getReservedList(
        // TODO 변경 필요 (현재 가데이터)
        userSeq: Int = TEST_USER_SEQ,
        itemCount: Int = PAGE_SIZE
    ) {
        viewModelScope.launch {
            getReservedListPaging(
                userSeq,
                itemCount
            ).collect { pagingData ->
                _reservedListPagingDataFlow.emit(pagingData)
            }
        }
    }
    private fun getReservedListPaging(
        userSeq: Int,
        itemCount: Int
    ): Flow<PagingData<TicketDto>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
            ReservedListPagingSource(
                reserveRepository,
                userSeq,
                itemCount
            ) {
                viewModelScope.launch {
                    if (it is SocketTimeoutException) {
                        _error.emit(DataThrowable.NetworkTrafficThrowable())
                    } else {
                        _error.emit(DataThrowable.NetworkErrorThrowable())
                    }
                }
            }
        }.flow.cachedIn(viewModelScope)
    }

    // TODO api연결
    fun confirmReservationCancel() {
        viewModelScope.launch {
            _refreshEvent.emit(true)
        }
    }

    companion object {
        private const val PAGE_SIZE = 10
        private const val TEST_USER_SEQ = 1
    }
}