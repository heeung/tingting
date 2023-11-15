package com.alsif.tingting.ui.reservedlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseBottomSheet
import com.alsif.tingting.databinding.BottomSheetReservationCancelBinding
import com.alsif.tingting.ui.main.MainActivityViewModel
import com.alsif.tingting.util.AnimUtil
import com.alsif.tingting.util.scaleAnimation
import javax.inject.Inject

class ReservationCancelModalBottomSheet @Inject constructor (
    private val viewModel: ReservedListFragmentViewModel,
) : BaseBottomSheet<BottomSheetReservationCancelBinding>(BottomSheetReservationCancelBinding::bind, R.layout.bottom_sheet_reservation_cancel)  {
    private val sharedViewModel: MainActivityViewModel by activityViewModels()
//    private val viewModel: ReservedListFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setClickListeners() {
        binding.buttonConfirm.setOnTouchListener { view, motionEvent ->
            when (motionEvent.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    view.scaleAnimation(AnimUtil.AnimDirection.XY, 0.94f, 100)
                }
                MotionEvent.ACTION_CANCEL -> {
                    view.scaleAnimation(AnimUtil.AnimDirection.XY, 1f, 100)
                }
                MotionEvent.ACTION_UP -> {
                    viewModel.confirmReservationCancel()
                    view.scaleAnimation(AnimUtil.AnimDirection.XY, 1f, 100)
                }
            }
            true
        }
    }

    companion object {
        const val TAG = "LoginBottomSheet"
    }
}