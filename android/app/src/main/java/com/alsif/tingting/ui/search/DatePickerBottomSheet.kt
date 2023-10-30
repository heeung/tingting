package com.alsif.tingting.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.alsif.tingting.R
import com.alsif.tingting.TingtingApp.DependencyContainer.authDataSource
import com.alsif.tingting.base.BaseBottomSheet
import com.alsif.tingting.data.local.AuthDataSource
import com.alsif.tingting.databinding.BottomSheetDatepickerBinding
import com.alsif.tingting.databinding.BottomSheetLoginBinding
import com.alsif.tingting.ui.login.kakaoLogin
import com.alsif.tingting.ui.main.MainActivityViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.core.util.Pair

class DatePickerBottomSheet : BaseBottomSheet<BottomSheetDatepickerBinding>(BottomSheetDatepickerBinding::bind, R.layout.bottom_sheet_datepicker)  {
    private val sharedViewModel: MainActivityViewModel by activityViewModels()

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO 아래로 내리는 동작 datepicker먼저 먹게
    }

    companion object {
        const val TAG = "LoginBottomSheet"
    }
}