package com.alsif.tingting.ui.search

import android.os.Build
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
import com.alsif.tingting.data.paging.SearchPagingSource
import com.alsif.tingting.data.repository.SearchRepository
import com.alsif.tingting.data.throwable.DataThrowable
import com.alsif.tingting.ui.home.HomeFragmentViewModel
import com.alsif.tingting.ui.search.recyclerview.SearchPagingAdapter
import com.alsif.tingting.util.extension.parseLocalDateTime
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.time.LocalDateTime
import javax.inject.Inject

private const val TAG = "SearchFragmentViewModel"
@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val searchRepository: SearchRepository
)  : ViewModel() {

//    private val _searchPagingDataFlow = MutableSharedFlow<PagingData<ConcertDto>>()
//    val searchPagingDataFlow = _searchPagingDataFlow.asSharedFlow()

    private val _searchPagingDataFlow = MutableStateFlow<PagingData<ConcertDto>>(PagingData.empty())
    val searchPagingDataFlow = _searchPagingDataFlow.asStateFlow()

    private val _isFilterOpened = MutableStateFlow(false)
    val isFilterOpened = _isFilterOpened.asStateFlow()

    private val _isDateEditPossible = MutableStateFlow(false)
    val isDateEditPossible = _isDateEditPossible.asStateFlow()

    private val _startDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        MutableStateFlow<Long>(MaterialDatePicker.thisMonthInUtcMilliseconds())
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    val startDate = _startDate.asStateFlow()

    private val _endDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        MutableStateFlow<Long>(MaterialDatePicker.todayInUtcMilliseconds())
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    val endDate = _endDate.asStateFlow()

    private val _error = MutableSharedFlow<DataThrowable>()
    var error = _error.asSharedFlow()

    /*
    콘서트 리스트
     */
    fun getConcertList(
        concertListRequestDto: ConcertListRequestDto
    ) {
        viewModelScope.launch {
            getConcertListPaging(
                concertListRequestDto
            ).collect { pagingData ->
                _searchPagingDataFlow.emit(pagingData)
            }
        }
    }
    private fun getConcertListPaging(
        concertListRequestDto: ConcertListRequestDto
    ): Flow<PagingData<ConcertDto>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
            SearchPagingSource(
                searchRepository,
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

    /**
     * 필터 영역 토글
     */
    fun toggleFilter() {
        viewModelScope.launch {
            _isFilterOpened.emit(!_isFilterOpened.value)
        }
    }

    /**
     * 날짜 선택 토글
     */
    fun toggleDateEdit() {
        viewModelScope.launch {
            _isDateEditPossible.emit(!_isDateEditPossible.value)
        }
    }

    /**
     * set date
     */
    fun setStartDate(date: Long) {
        viewModelScope.launch {
            _startDate.emit(date)
        }
    }

    fun setEndDate(date: Long) {
        viewModelScope.launch {
            _endDate.emit(date)
        }
    }

    companion object {
        private const val PAGE_SIZE = 10
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: Search 뷰모델 죽음")
    }
}