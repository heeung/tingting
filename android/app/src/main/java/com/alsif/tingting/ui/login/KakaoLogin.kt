package com.alsif.tingting.ui.login

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.alsif.tingting.data.local.AuthDataSource
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

/**
 * 카카오 SDK 로그인 구현부입니다.
 */
fun startKakaoLogin(
    context: Context,
    authDataSource: AuthDataSource,
) {
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(ContentValues.TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(ContentValues.TAG, "카카오톡으로 로그인 성공 accesstoken ${token.accessToken}")
            Log.i(ContentValues.TAG, "카카오톡으로 로그인 성공 refreshtoken ${token.refreshToken}")
            val email = token.scopes!![0]

            authDataSource.setAccessToken(token.accessToken)
            authDataSource.setRefreshToken(token.refreshToken)
        }
    }
    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
}