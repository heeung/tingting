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
import com.alsif.tingting.data.paging.ConcertPagingSource
import com.alsif.tingting.data.repository.HomeRepository
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
    private val homeRepository: HomeRepository
)  : ViewModel() {
    private val _concertDetail = MutableStateFlow(ConcertDetailDto())
    val concertDetail = _concertDetail.asStateFlow()

    private val _error = MutableSharedFlow<DataThrowable>()
    var error = _error.asSharedFlow()

    //////콘서트 디테일///////
    fun getConcertDetail(concertSeq: Int, userSeq: Int) {
        viewModelScope.launch {
            runCatching {
                homeRepository.getConcertDetail(concertSeq, userSeq)
            }.onSuccess {
                _concertDetail.emit(it)
            }.onFailure {
                _error.emit(it as DataThrowable)
            }
        }
    }
    ///////////////////////

    companion object {
        private const val PAGE_SIZE = 10
    }
}