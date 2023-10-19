package com.alsif.tingting.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alsif.tingting.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LoginModalBottomSheet : BottomSheetDialogFragment()  {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.bottom_sheet_login, container, false)
    }

    companion object {
        const val TAG = "BasicBottomModalSheet"
    }
}