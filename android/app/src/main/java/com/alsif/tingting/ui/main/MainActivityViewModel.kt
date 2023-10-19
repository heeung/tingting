package com.alsif.tingting.ui.main

import androidx.lifecycle.ViewModel
import com.alsif.tingting.data.local.AuthDataSource
import com.alsif.tingting.data.model.CommentDto
import com.alsif.tingting.data.repository.BaseRepository
import com.alsif.tingting.data.throwable.DataThrowable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val authDataSource: AuthDataSource
) : ViewModel() {

//    private val _isLogined = MutableStateFlow(false)
//    var isLogined = _isLogined.asStateFlow()

    private val _error = MutableSharedFlow<DataThrowable>()
    var error = _error.asSharedFlow()

    /** 저장된 AccessToken을 불러옵니다 만약 저장된 정보가 없으면 null을 반환합니다. */
    fun getAccessToken() : String? {
        return authDataSource.getAccessToken()
    }
}