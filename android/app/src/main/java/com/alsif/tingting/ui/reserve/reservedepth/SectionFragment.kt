package com.alsif.tingting.ui.reserve.reservedepth

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.databinding.FragmentReserveBinding
import com.alsif.tingting.databinding.FragmentSectionBinding
import com.alsif.tingting.ui.reserve.ReserveFragment
import com.alsif.tingting.ui.reserve.ReserveFragmentArgs
import com.alsif.tingting.ui.reserve.ReserveFragmentViewModel
import com.alsif.tingting.util.AnimUtil
import com.alsif.tingting.util.scaleAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SectionFragment @Inject constructor(
    private val viewModel : ReserveFragmentViewModel,
    private val mFragment: ReserveFragment
) : BaseFragment<FragmentSectionBinding>(FragmentSectionBinding::bind, com.alsif.tingting.R.layout.fragment_section) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.sectionVip1.touchListener {
            viewModel.setSectino(SECTION_A)
        }
        binding.sectionVip2.touchListener {
            viewModel.setSectino(SECTION_B)
        }
        binding.sectionVip3.touchListener {
            viewModel.setSectino(SECTION_C)
        }
        binding.sectionVip4.touchListener {
            viewModel.setSectino(SECTION_D)
        }
        binding.sectionCommon1.touchListener {
            viewModel.setSectino(SECTION_E)
        }
        binding.sectionCommon2.touchListener {
            viewModel.setSectino(SECTION_F)
        }
        binding.sectionCommon3.touchListener {
            viewModel.setSectino(SECTION_G)
        }
        binding.sectionCommon4.touchListener {
            viewModel.setSectino(SECTION_H)
        }
        binding.sectionCommon5.touchListener {
            viewModel.setSectino(SECTION_I)
        }
        binding.sectionCommon6.touchListener {
            viewModel.setSectino(SECTION_J)
        }
    }

    private fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.nowSection.collectLatest {
                mFragment.showLoadingDialog()
                mFragment.changeFragment(SELECT_SEAT_FRAGMENT_NAME, it)
                viewModel.getSeatList(mFragment.args.concertDetailSeq, mFragment.args.concertHallSeq, it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        viewModel.resetSelection()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun View.touchListener(
        onClick: () -> Unit
    ) {
        this.setOnTouchListener { view, motionEvent ->
            when (motionEvent.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    view.scaleAnimation(AnimUtil.AnimDirection.XY, 0.94f, 100)
                }

                MotionEvent.ACTION_CANCEL -> {
                    view.scaleAnimation(AnimUtil.AnimDirection.XY, 1f, 100)
                }

                MotionEvent.ACTION_UP -> {
                    onClick()
                    view.scaleAnimation(AnimUtil.AnimDirection.XY, 1f, 100)
                }
            }
            true
        }
    }

    companion object {
        private const val SECTION_FRAGMENT_NAME = "section"
        private const val SELECT_SEAT_FRAGMENT_NAME = "select_seat"

        private const val SECTION_A = "A"
        private const val SECTION_B = "B"
        private const val SECTION_C = "C"
        private const val SECTION_D = "D"
        private const val SECTION_E = "E"
        private const val SECTION_F = "F"
        private const val SECTION_G = "G"
        private const val SECTION_H = "H"
        private const val SECTION_I = "I"
        private const val SECTION_J = "J"
    }
}