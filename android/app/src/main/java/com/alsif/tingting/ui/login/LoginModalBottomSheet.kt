package com.alsif.tingting.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.alsif.tingting.R
import com.alsif.tingting.TingtingApp.DependencyContainer.authDataSource
import com.alsif.tingting.base.BaseBottomSheet
import com.alsif.tingting.data.local.AuthDataSource
import com.alsif.tingting.databinding.BottomSheetLoginBinding
import com.alsif.tingting.ui.main.MainActivityViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LoginModalBottomSheet : BaseBottomSheet<BottomSheetLoginBinding>(BottomSheetLoginBinding::bind, R.layout.bottom_sheet_login)  {
    private val sharedViewModel: MainActivityViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnKakaoLogin.setOnClickListener {
            kakaoLogin(
                mActivity,
                setAccessToken = {
                    sharedViewModel.setAccessToken(it)
                },
                setRefreshToken = {
                    sharedViewModel.setRefreshToken(it)
                },
                loginEvent = {
                    sharedViewModel.loginEvent()
                }
            )
        }
    }

    companion object {
        const val TAG = "LoginBottomSheet"
    }
}