package com.alsif.tingting.ui.reserve

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.databinding.FragmentHomeBinding
import com.alsif.tingting.databinding.FragmentReserveBinding
import com.alsif.tingting.ui.concertdetail.ConcertDetailFragment
import com.alsif.tingting.ui.concertdetail.ConcertDetailFragmentArgs
import com.alsif.tingting.ui.concertdetail.ConcertDetailFragmentViewModel
import com.alsif.tingting.ui.home.HomeFragmentViewModel
import com.alsif.tingting.ui.home.viewpager.AdsViewPagerAdapter
import com.alsif.tingting.ui.home.viewpager.TabViewPagerAdapter
import com.alsif.tingting.ui.login.LoginModalBottomSheet
import com.alsif.tingting.ui.reserve.recyclerview.SelectSeatAdapter
import com.alsif.tingting.ui.reserve.recyclerview.SelectSeatListAdapter
import com.alsif.tingting.ui.reserve.reservedepth.SectionFragment
import com.alsif.tingting.ui.reserve.reservedepth.SelectSeatFragment
import com.alsif.tingting.ui.reservedlist.ReservationCancelModalBottomSheet
import com.alsif.tingting.util.AnimUtil
import com.alsif.tingting.util.extension.setCurrentItemWithDuration
import com.alsif.tingting.util.scaleAnimation
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private const val TAG = "ReserveFragment"
@AndroidEntryPoint
class ReserveFragment : BaseFragment<FragmentReserveBinding>(FragmentReserveBinding::bind, R.layout.fragment_reserve) {

    private val viewModel : ReserveFragmentViewModel by viewModels()
    val args: ReserveFragmentArgs by navArgs()

//    lateinit var seatListAdapter: SelectSeatAdapter
    lateinit var seatListAdapter: SelectSeatListAdapter

    private val reservationConfirmBottomSheet by lazy {
        ReservationConfirmModalBottomSheet(
            viewModel,
            viewModel.selectListList.value.sumOf { a -> a.price },
            args.concertDetailSeq,
            viewModel.selectListList.value[0].section,
            viewModel.selectListList.value.map{ it.concertSeatInfoSeq }
        ) { showReservationConfirmBottomSheet() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        subscribe()
        setClickListeners()
        changeFragment(SECTION_FRAGMENT_NAME)
        Log.d(TAG, "onViewCreated: ${args.concertDetailSeq}, ${args.concertHallSeq}")
    }

    fun initRecyclerView() {
        seatListAdapter = SelectSeatListAdapter(viewModel.selectListList.value)
        binding.recyclerSelectSeat.adapter = seatListAdapter
        binding.recyclerSelectSeat.layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
//        binding.recyclerSelectSeats.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        seatListAdapter.notifyDataSetChanged()
    }

    private fun setClickListeners() {
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.buttonReservation.touchListener {
            val concertDetailSeq = args.concertDetailSeq
            val list = viewModel.selectListList.value.map { it ->
                it.concertSeatInfoSeq
            }
            showLoadingDialog()
            viewModel.getIsPossibleSeats(concertDetailSeq, list)
        }
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

    fun changeFragment(name: String, section: String = "A") {
        when(name) {
            SECTION_FRAGMENT_NAME -> {
                childFragmentManager.beginTransaction()
                    .replace(R.id.layout_reservation_frame, SectionFragment(viewModel, this))
                    .commit()
            }
            SELECT_SEAT_FRAGMENT_NAME -> {
                childFragmentManager.beginTransaction()
                    .replace(R.id.layout_reservation_frame, SelectSeatFragment(viewModel, this, section))
                    .addToBackStack(name)
                    .commit()
            }
            else -> {

            }
        }
    }

    private fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectListSize.collectLatest {
                binding.textviewTotalPrice.text = "티켓금액      ${viewModel.selectListList.value.sumOf { a -> a.price }} 원"
                if (it == 0)
                    binding.layoutReserveConfirm.visibility = View.GONE
                else
                    binding.layoutReserveConfirm.visibility = View.VISIBLE
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isPossibleSeats.collect {
                dismissLoadingDialog()
                if (it) {
//                    showSnackbar(binding.root, POSSIBLE_MESSAGE)
                    showReservationConfirmBottomSheet()
                } else {
                    showSnackbar(binding.root, IMPOSSIBLE_MESSAGE)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isReservationSuccess.collectLatest {
                childFragmentManager.beginTransaction()
                    .replace(R.id.layout_reservation_frame, SectionFragment(viewModel, this@ReserveFragment))
                    .commit()
                dismissLoadingDialog()
                if (it) {
                    viewModel.resetSelection()
                    initRecyclerView()
                    dismissReservationConfirmBottomSheet()
                    showSnackbar(binding.root, SUCCESS_MESSAGE, "확인하러 가기") {
                        mActivity.navController.navigate(R.id.reservedListFragment)
                    }
                    findNavController().popBackStack()
                } else {
                    dismissReservationConfirmBottomSheet()
                    showSnackbar(binding.root, FAIL_MESSAGE)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collectLatest {
                dismissLoadingDialog()
                dismissReservationConfirmBottomSheet()
                showToast(it.message.toString())
            }
        }
    }

    private fun showReservationConfirmBottomSheet() {
        if (!reservationConfirmBottomSheet.isAdded) {
            reservationConfirmBottomSheet.show(childFragmentManager, LoginModalBottomSheet.TAG)
        }
    }

    private fun dismissReservationConfirmBottomSheet() {
        reservationConfirmBottomSheet.dismiss()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private const val SECTION_FRAGMENT_NAME = "section"
        private const val SELECT_SEAT_FRAGMENT_NAME = "select_seat"

        private const val POSSIBLE_MESSAGE = "예매가 가능한 좌석입니다."
        private const val IMPOSSIBLE_MESSAGE = "예매가 불가능한 좌석입니다. 다시 시도해주세요!"

        private const val SUCCESS_MESSAGE = "예매에 성공했습니다."
        private const val FAIL_MESSAGE = "예매 실패했습니다.. 다시 시도해주세요!"
    }
}