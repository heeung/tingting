package com.alsif.tingting.ui.login

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.alsif.tingting.TingtingApp.DependencyContainer.authDataSource
import com.alsif.tingting.data.local.AuthDataSource
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import javax.inject.Inject

private const val TAG = "KakaoLogin"
/**
 * 카카오 SDK 로그인 구현부입니다.
 */
fun kakaoLogin(
    context: Context,
    setAccessToken: (String) -> Unit,
    setRefreshToken: (String) -> Unit,
    loginEvent: () -> Unit
) {
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        Log.d(TAG, "kakaoLogin: 콜백이 불렸어요")
        if (error != null) {
            Log.e(ContentValues.TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            setAccessToken(token.accessToken)
            setRefreshToken(token.refreshToken)
            loginEvent()

            Log.i(ContentValues.TAG, "카카오톡으로 로그인 성공 accesstoken ${token.accessToken}")
            Log.i(ContentValues.TAG, "카카오톡으로 로그인 성공 refreshtoken ${token.refreshToken}")
        }
    }

    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        // 카카오톡 설치시
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) { // 로그인 실패한 경우
                Log.e("[김희웅] Kakao: ", "카카오톡 로그인 실패 (카카오톡이 설치되어있음)", error)
            }
            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                return@loginWithKakaoTalk
            }

            // 위의 에러 종류가 아니라면
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    } else {
        // 카카오톡 미설치시
        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
    }
}