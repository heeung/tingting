package com.alsif.tingting.ui.reservedlist

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
import com.alsif.tingting.databinding.BottomSheetTicketDetailBinding
import com.alsif.tingting.ui.login.kakaoLogin
import com.alsif.tingting.ui.main.MainActivityViewModel
import com.alsif.tingting.util.clickAnimation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TicketDetailModalBottomSheet : BaseBottomSheet<BottomSheetTicketDetailBinding>(BottomSheetTicketDetailBinding::bind, R.layout.bottom_sheet_ticket_detail)  {
    private val sharedViewModel: MainActivityViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        const val TAG = "LoginBottomSheet"
    }
}