package com.alsif.tingting.ui.concertdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alsif.tingting.data.model.ConcertDetailDto
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.PagerDataDto
import com.alsif.tingting.data.model.request.ConcertListRequestDto
import com.alsif.tingting.data.model.request.LikeToggleRequestDto
import com.alsif.tingting.data.paging.ConcertPagingSource
import com.alsif.tingting.data.repository.HomeRepository
import com.alsif.tingting.data.repository.LikeRepository
import com.alsif.tingting.data.throwable.DataThrowable
import com.alsif.tingting.ui.home.dummy.adList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConcertDetailFragmentViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val likeRepository: LikeRepository
)  : ViewModel() {
    private val _concertDetail = MutableStateFlow(ConcertDetailDto())
    val concertDetail = _concertDetail.asStateFlow()

    private val _isLiked = MutableStateFlow<Boolean>(false)
    var isLiked = _isLiked.asStateFlow()

    private val _error = MutableSharedFlow<DataThrowable>()
    var error = _error.asSharedFlow()

    fun setIsLiked(value: Boolean) {
        viewModelScope.launch {
            _isLiked.emit(value)
        }
    }

    //////콘서트 디테일///////
    fun getConcertDetail(concertSeq: Int, userSeq: Int) {
        viewModelScope.launch {
            runCatching {
                homeRepository.getConcertDetail(concertSeq, userSeq)
            }.onSuccess {
                _concertDetail.emit(it.apply {
                    it.holdOpenDate = it.holdOpenDate.subSequence(0, 10).toString()
                    it.holdCloseDate = it.holdCloseDate.subSequence(0, 10).toString()
                })
            }.onFailure {
                if (it is DataThrowable) {
                    _error.emit(it)
                } else {
                    _error.emit(DataThrowable.IllegalStateThrowable())
                }
            }
        }
    }
    ///////////////////////

    /**
     * 찜 토글
     */
    fun postLike(type: String, userSeq: Int) {
        val likeToggleRequestDto = LikeToggleRequestDto(concertDetail.value.concertSeq, userSeq)
        viewModelScope.launch {
            if (type == "찜 하기" && concertDetail.value.favorite) { // 두개가 달라야 api요청 하면 됨
                likeRepository.postLike(likeToggleRequestDto)
            }
            if (type == "찜 취소" && !concertDetail.value.favorite) {
                likeRepository.postLike(likeToggleRequestDto)
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 10

    }
}