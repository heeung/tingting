package com.alsif.tingting.ui.home

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
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val homeRepository: HomeRepository
)  : ViewModel() {

    private val _onSalePagingDataFlow = MutableStateFlow<PagingData<ConcertDto>>(PagingData.empty())
    val onSalePagingDataFlow = _onSalePagingDataFlow.asStateFlow()

    private val _soonSalePagingDataFlow = MutableStateFlow<PagingData<ConcertDto>>(PagingData.empty())
    val soonSalePagingDataFlow = _soonSalePagingDataFlow.asStateFlow()

    private val _error = MutableSharedFlow<DataThrowable>()
    var error = _error.asSharedFlow()

    ////// for ads banner //////
    lateinit var job: Job
    var bannerPosition = 0

    // TODO 더미 데이터 변경
    val pagerList: ArrayList<PagerDataDto> = ArrayList<PagerDataDto>().let {
        it.apply {
            add(PagerDataDto(adList[0]))
            add(PagerDataDto(adList[1]))
            add(PagerDataDto(adList[2]))
            add(PagerDataDto(adList[3]))
            add(PagerDataDto(adList[4]))
        }
    }
    ///////////////////////////

    /*
    콘서트 리스트
     */
    fun getOnSaleConcertList(
        concertListRequestDto: ConcertListRequestDto
    ) {
        viewModelScope.launch {
            getOnSaleConcertListPaging(
                concertListRequestDto
            ).collectLatest { pagingData ->
                _onSalePagingDataFlow.emit(pagingData)
            }
        }
    }
    fun getSoonSaleConcertList(
        concertListRequestDto: ConcertListRequestDto
    ) {
        viewModelScope.launch {
            getSoonSaleConcertListPaging(
                concertListRequestDto
            ).collectLatest { pagingData ->
                _soonSalePagingDataFlow.emit(pagingData)
            }
        }
    }
    private fun getOnSaleConcertListPaging(
        concertListRequestDto: ConcertListRequestDto
    ): Flow<PagingData<ConcertDto>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
            ConcertPagingSource(
                homeRepository,
                concertListRequestDto
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
    private fun getSoonSaleConcertListPaging(
        concertListRequestDto: ConcertListRequestDto
    ): Flow<PagingData<ConcertDto>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
            ConcertPagingSource(
                homeRepository,
                concertListRequestDto
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

    companion object {
        private const val PAGE_SIZE = 10
    }
}