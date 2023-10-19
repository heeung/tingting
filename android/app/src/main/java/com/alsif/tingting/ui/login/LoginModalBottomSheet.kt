package com.alsif.tingting.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.alsif.tingting.R
import com.alsif.tingting.TingtingApp.DependencyContainer.authDataSource
import com.alsif.tingting.data.local.AuthDataSource
import com.alsif.tingting.databinding.BottomSheetLoginBinding
import com.alsif.tingting.ui.main.MainActivityViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LoginModalBottomSheet : BottomSheetDialogFragment()  {
    private val sharedViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.bottom_sheet_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.btnKakaoLogin.setOnClickListener {
////            startKakaoLogin()
//        }
    }

    companion object {
        const val TAG = "BasicBottomModalSheet"
    }
}