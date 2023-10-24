package com.alsif.tingting.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alsif.tingting.data.local.AuthDataSource
import com.alsif.tingting.data.throwable.DataThrowable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainActivityViewModel"
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val authDataSource: AuthDataSource
) : ViewModel() {

    private val _isLogined = MutableSharedFlow<Boolean>()
    var isLogined = _isLogined.asSharedFlow()

    private val _error = MutableSharedFlow<DataThrowable>()
    var error = _error.asSharedFlow()

    /** 저장된 AccessToken을 불러옵니다 만약 저장된 정보가 없으면 null을 반환합니다. */
    fun getAccessToken() : String? {
        return authDataSource.getAccessToken()
    }
    /** 저장된 RefreshToken을 불러옵니다 만약 저장된 정보가 없으면 null을 반환합니다. */
    fun getRefreshToken() : String? {
        return authDataSource.getAccessToken()
    }
    /** AccessToken을 저장합니다. */
    fun setAccessToken(newToken: String) {
        authDataSource.setAccessToken(newToken)
    }
    /** RefreshToken을 저장합니다. */
    fun setRefreshToken(newToken: String) {
        authDataSource.setRefreshToken(newToken)
    }
    /** 토큰들을 비웁니다. */
    fun resetToken() {
        authDataSource.resetToken()
        logoutEvent()
    }
    /** 로그인 성공시 처리를 위한 함수 */
    fun loginEvent() {
        viewModelScope.launch {
            _isLogined.emit(true)
        }
    }
    fun logoutEvent() {
        viewModelScope.launch {
            _isLogined.emit(false)
        }
    }
}