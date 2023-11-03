package com.alsif.tingting.ui.likedlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.request.ConcertListRequestDto
import com.alsif.tingting.data.paging.ConcertPagingSource
import com.alsif.tingting.data.paging.LikedListPagingSource
import com.alsif.tingting.data.repository.LikeRepository
import com.alsif.tingting.data.throwable.DataThrowable
import com.alsif.tingting.ui.home.HomeFragmentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

private const val TAG = "LikedListFragmentViewMo"
@HiltViewModel
class LikedListFragmentViewModel @Inject constructor(
    private val likeRepository: LikeRepository
)  : ViewModel() {

    private val _likedListPagingDataFlow = MutableStateFlow<PagingData<ConcertDto>>(PagingData.empty())
    val likedListPagingDataFlow = _likedListPagingDataFlow.asStateFlow()

    private val _error = MutableSharedFlow<DataThrowable>()
    var error = _error.asSharedFlow()

    /*
    찜 리스트
     */
    fun getLikedConcertList(
        // TODO 변경 필요 (현재 가데이터)
        userSeq: Int = TEST_USER_SEQ,
        itemCount: Int = PAGE_SIZE
    ) {
        viewModelScope.launch {
            Log.d(TAG, "getLikedConcertList: getLike")
            getLikedConcertListPaging(
                userSeq,
                itemCount
            ).collect { pagingData ->
                Log.d(TAG, "getLikedConcertList: 콜렉트 스코프 들어옴")
                _likedListPagingDataFlow.emit(pagingData)
            }
        }
    }
    private fun getLikedConcertListPaging(
        userSeq: Int,
        itemCount: Int
    ): Flow<PagingData<ConcertDto>> {
        Log.d(TAG, "getLikedConcertListPaging: 불러옵니다.")
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
            LikedListPagingSource(
                likeRepository,
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

//    val pagingFlow: (Int, Int) -> Flow<PagingData<ConcertDto>> = {userSeq: Int, itemCount: Int ->
//        Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
//            LikedListPagingSource(
//                likeRepository,
//                userSeq,
//                itemCount
//            ) {
//                viewModelScope.launch {
//                    _error.emit(DataThrowable.NetworkErrorThrowable())
//                }
//            }
//        }.flow.cachedIn(viewModelScope)
//    }

    companion object {
        private const val PAGE_SIZE = 10
        private const val TEST_USER_SEQ = 1
    }
}