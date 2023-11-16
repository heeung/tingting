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
import com.alsif.tingting.data.model.request.ReserveRequestDto
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

    private val _selectList = MutableStateFlow<HashMap<Long, SeatSelectionDto>>(hashMapOf())
    var selectList = _selectList.asStateFlow()

    private val _selectListList = MutableStateFlow<MutableList<SeatSelectionDto>>(mutableListOf())
    var selectListList = _selectListList.asStateFlow()

    private val _selectListSize = MutableStateFlow(0)
    var selectListSize = _selectListSize.asStateFlow()

    private val _isReservationSuccess = MutableSharedFlow<Boolean>()
    var isReservationSuccess = _isReservationSuccess.asSharedFlow()

    private val _isPossibleSeats = MutableSharedFlow<Boolean>()
    var isPossibleSeats = _isPossibleSeats.asSharedFlow()

    fun setSectino(section: String) {
        viewModelScope.launch {
            _nowSection.emit(section)
        }
    }

    fun selectSeat(dto: SeatSelectionDto) {
        viewModelScope.launch {
            _selectList.value[dto.concertSeatInfoSeq] = dto
            _selectListList.value.add(dto)
            _selectListSize.emit(_selectList.value.size)
        }
//        for(d in _selectList.value) {
//            Log.d(TAG, "selectSeat: $d")
//        }
    }

    fun unSelectSeat(dto: SeatSelectionDto) {
        viewModelScope.launch {
            _selectList.value.remove(dto.concertSeatInfoSeq)
            _selectListList.value.remove(dto)
            _selectListSize.emit(_selectList.value.size)
        }
//        for(d in _selectList.value) {
//            Log.d(TAG, "selectSeat: $d")
//        }
    }

    fun resetSelection() {
        _selectList.value = hashMapOf()
        _selectListList.value = mutableListOf()
    }

    fun getIsPossibleSeats(concertDetailSeq: Int, seatSeqs: List<Long>) {
        viewModelScope.launch {
            runCatching{
                reserveRepository.getIsPossibleSeat(concertDetailSeq, seatSeqs)
            }.onSuccess {
                if (it.message == "true")
                    _isPossibleSeats.emit(true)
                else
                    _isPossibleSeats.emit(false)
            }.onFailure {
                if (it is SocketTimeoutException) {
                    _error.emit(DataThrowable.NetworkTrafficThrowable())
                } else {
                    _error.emit(DataThrowable.NetworkErrorThrowable())
                }
            }
        }
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

    fun postReservation(
        concertDetailSeq: Int,
        userSeq: Int,
        section: String,
        seatSeqs: ReserveRequestDto
    ) {
        viewModelScope.launch {
            runCatching {
                reserveRepository.postReservation(concertDetailSeq, userSeq, section, seatSeqs)
            }.onSuccess {
                if (it.message == "true") {
                    _isReservationSuccess.emit(true)
                } else {
                    _isReservationSuccess.emit(false)
                }
            }.onFailure {
                if (it is DataThrowable.Base400Throwable) {
                    _error.emit(it)
                } else if (it is SocketTimeoutException) {
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