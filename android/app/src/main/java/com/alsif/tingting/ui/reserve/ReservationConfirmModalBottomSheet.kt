package com.alsif.tingting.ui.reserve

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseBottomSheet
import com.alsif.tingting.data.model.request.ReserveRequestDto
import com.alsif.tingting.databinding.BottomSheetReservationCancelBinding
import com.alsif.tingting.databinding.BottomSheetReservationConfirmBinding
import com.alsif.tingting.ui.main.MainActivityViewModel
import com.alsif.tingting.ui.reservedlist.ReservedListFragmentViewModel
import com.alsif.tingting.util.AnimUtil
import com.alsif.tingting.util.scaleAnimation
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReservationConfirmModalBottomSheet @Inject constructor (
    private val viewModel: ReserveFragmentViewModel,
    private val totalPrice: Int,
    private val concertDetailSeq: Int,
    private val section: String,
    private val seatSeqs: List<Long>,
    private val loading: () -> Unit
) : BaseBottomSheet<BottomSheetReservationConfirmBinding>(BottomSheetReservationConfirmBinding::bind, R.layout.bottom_sheet_reservation_confirm)  {
    private val sharedViewModel: MainActivityViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()
        subscribe()
        sharedViewModel.getMyMoneyInfo()
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
                    loading()
                    viewModel.postReservation(concertDetailSeq, TEST_USER_SEQ, section, ReserveRequestDto(seatSeqs))
                    view.scaleAnimation(AnimUtil.AnimDirection.XY, 1f, 100)
                }
            }
            true
        }
    }

    private fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.myMoney.collectLatest {
                binding.textviewMyMoney.text = "현재 잔액 : ${it} 원"
                binding.textviewTotalPrice.text= "결제 금액 : ${totalPrice} 원"
            }
        }
    }

    companion object {
        const val TAG = "LoginBottomSheet"

        const val TEST_USER_SEQ = 1
    }
}