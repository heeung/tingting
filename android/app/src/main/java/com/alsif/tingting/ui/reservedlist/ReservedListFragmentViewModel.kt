package com.alsif.tingting.ui.reservedlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alsif.tingting.data.model.CommentDto
import com.alsif.tingting.data.repository.BaseRepository
import com.alsif.tingting.data.throwable.DataThrowable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservedListFragmentViewModel @Inject constructor(
    
)  : ViewModel() {

    private val _error = MutableSharedFlow<DataThrowable>()
    var error = _error.asSharedFlow()
}