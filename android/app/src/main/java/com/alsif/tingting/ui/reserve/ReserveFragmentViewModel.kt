package com.alsif.tingting.ui.reserve

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.SeatSelectionDto
import com.alsif.tingting.data.paging.LikedListPagingSource
import com.alsif.tingting.data.repository.LikeRepository
import com.alsif.tingting.data.repository.ReserveRepository
import com.alsif.tingting.data.throwable.DataThrowable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

private const val TAG = "ReserveFragmentViewMode"
@HiltViewModel
class ReserveFragmentViewModel @Inject constructor(
    private val reserveRepository: ReserveRepository
)  : ViewModel() {

    private val _error = MutableSharedFlow<DataThrowable>()
    var error = _error.asSharedFlow()

    private val _nowSection = MutableSharedFlow<String>()
    var nowSection = _nowSection.asSharedFlow()

    private val _seatList = MutableStateFlow<List<SeatSelectionDto>>(emptyList())
    var seatList = _seatList.asStateFlow()

    private val _selectList = MutableStateFlow<HashMap<String, SeatSelectionDto>>(hashMapOf())
    var selectList = _selectList.asStateFlow()

    private val _selectListSize = MutableStateFlow(0)
    var selectListSize = _selectListSize.asStateFlow()

    fun setSectino(section: String) {
        viewModelScope.launch {
            _nowSection.emit(section)
        }
    }

    fun selectSeat(dto: SeatSelectionDto) {
        viewModelScope.launch {
            _selectList.value[dto.seat] = dto
            _selectListSize.emit(_selectList.value.size)
        }
//        for(d in _selectList.value) {
//            Log.d(TAG, "selectSeat: $d")
//        }
    }

    fun unSelectSeat(dto: SeatSelectionDto) {
        viewModelScope.launch {
            _selectList.value.remove(dto.seat)
            _selectListSize.emit(_selectList.value.size)
        }
//        for(d in _selectList.value) {
//            Log.d(TAG, "selectSeat: $d")
//        }
    }

    fun resetSelection() {
        _selectList.value = hashMapOf()
    }

    fun getSeatList(concertDetailSeq: Int, concertHallSeq: Int, target: String) {
        viewModelScope.launch {
            runCatching {
                reserveRepository.getSectionSeatList(concertDetailSeq, concertHallSeq, target)
            }.onSuccess {
                _seatList.emit(it)
            }.onFailure {
                if (it is SocketTimeoutException) {
                    _error.emit(DataThrowable.NetworkTrafficThrowable())
                } else {
                    _error.emit(DataThrowable.NetworkErrorThrowable())
                }
            }
        }
    }

    companion object {
        private const val TEST_USER_SEQ = 1

        private const val SECTION_A = "A"
        private const val SECTION_B = "B"
        private const val SECTION_C = "C"
        private const val SECTION_D = "D"
        private const val SECTION_E = "E"
        private const val SECTION_F = "F"
        private const val SECTION_G = "G"
        private const val SECTION_H = "H"
        private const val SECTION_I = "I"
        private const val SECTION_J = "J"
    }
}