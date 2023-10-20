package com.alsif.tingting.ui.likedlist

import androidx.lifecycle.ViewModel
import com.alsif.tingting.data.throwable.DataThrowable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class LikedListFragmentViewModel @Inject constructor(
    
)  : ViewModel() {

    private val _error = MutableSharedFlow<DataThrowable>()
    var error = _error.asSharedFlow()
}