package com.alsif.tingting

import android.app.Application
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.alsif.tingting.BuildConfig.API_KEY
import com.alsif.tingting.data.local.AuthDataSource
import com.alsif.tingting.data.local.AuthDataSourceImpl
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * 앱이 실행될 때 한번만 실행 됩니다.
 */
@HiltAndroidApp
class TingtingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        getHashKey()
        KakaoSdk.init(this, API_KEY)
//        disableDarkMode()
        initDataSources()
    }

    // 다크모드 x
    private fun disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    // 토큰을 preference에 저장하기 위함.
    private fun initDataSources() {
        authDataSource = AuthDataSourceImpl(applicationContext)
    }

    companion object DependencyContainer {
        lateinit var authDataSource: AuthDataSource
    }

    // 카카오 로그인을 위해 해시키를 발급합니다.
    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))

            } catch (e: NoSuchAlgorithmException) {
            }
        }
    }
}