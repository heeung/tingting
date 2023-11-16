package com.alsif.tingting.ui.reservedlist

import android.annotation.SuppressLint
import android.app.ProgressDialog.show
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.data.model.TicketDto
import com.alsif.tingting.databinding.FragmentReservedListBinding
import com.alsif.tingting.ui.likedlist.recyclerview.ReservedListPagingAdapter
import com.alsif.tingting.ui.login.LoginModalBottomSheet
import com.alsif.tingting.ui.main.MainActivityViewModel
import com.alsif.tingting.ui.search.recyclerview.PageLoadingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "ReservedListFragment"
@AndroidEntryPoint
class ReservedListFragment : BaseFragment<FragmentReservedListBinding>(FragmentReservedListBinding::bind, R.layout.fragment_reserved_list) {
    private val viewModel: ReservedListFragmentViewModel by viewModels()
    private val sharedViewModel: MainActivityViewModel by activityViewModels()

    private lateinit var reservedListAdapter: ReservedListPagingAdapter

    private val reservationCancelBottomSheet by lazy {
         ReservationCancelModalBottomSheet(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (mActivity.requireLogin()) {
            initRecyclerView()
            subscribe()
            setClickListeners()
        }
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.isFirstRander) {
            getReservedListWithLoadingDialog()
            viewModel.isFirstRander = !viewModel.isFirstRander
        }
    }

    private fun getReservedList() {
        viewModel.getReservedList()
    }

    private fun getReservedListWithLoadingDialog() {
        showLoadingDialog()
        viewModel.getReservedList()
    }

    private fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.reservedListPagingDataFlow.collectLatest {
                reservedListAdapter.submitData(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collectLatest {
                dismissLoadingDialog()
                showToast(it.message.toString())
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.refreshEvent.collectLatest {
                if (it) {
                    dismissReservationCancelBottomSheet()
                    showSnackbar(binding.root, CONFIRM_CANCEL_MESSAGE)
                    getReservedListWithLoadingDialog()
                }
            }
        }
    }

    private fun initRecyclerView() {
        reservedListAdapter = ReservedListPagingAdapter()
        binding.recyclerReservedList.apply {
            adapter = reservedListAdapter.withLoadStateFooter(
                footer = PageLoadingAdapter()
            )
            layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setClickListeners() {
        reservedListAdapter.itemClickListener = object: ReservedListPagingAdapter.ItemClickListener {
            @SuppressLint("ClickableViewAccessibility")
            override fun onClick(view: View, ticketDto: TicketDto) {
//                showReservationCancelBottomSheet()
            }
        }
        reservedListAdapter.reservationCancelClickListener = object: ReservedListPagingAdapter.ReservationCancelClickListener {
            @SuppressLint("ClickableViewAccessibility")
            override fun onClick(view: View, ticketDto: TicketDto) {
                viewModel.pickTicketSeq = ticketDto.ticketSeq
                showReservationCancelBottomSheet()
            }
        }
        reservedListAdapter.concertDatailClickListener = object: ReservedListPagingAdapter.ConcertDatailClickListener {
            @SuppressLint("ClickableViewAccessibility")
            override fun onClick(view: View, ticketDto: TicketDto) {
                val action = ReservedListFragmentDirections.actionReservedListFragmentToConcertDetailFragment(ticketDto.concertSeq)
                findNavController().navigate(action)
            }
        }
        binding.layoutSwipeRefresh.setOnRefreshListener {
            getReservedList()
        }
        reservedListAdapter.addOnPagesUpdatedListener {
            dismissLoadingDialog()
            binding.layoutSwipeRefresh.isRefreshing = false
//            if (reservedListAdapter.itemCount == 0) {
//                showSnackbar(binding.root, LikedListFragment.NEED_LIKE_MESSAGE)
//            }
        }
    }

    private fun showReservationCancelBottomSheet() {
        reservationCancelBottomSheet.show(childFragmentManager, LoginModalBottomSheet.TAG)
    }

    private fun dismissReservationCancelBottomSheet() {
        reservationCancelBottomSheet.dismiss()
    }

    override fun onDestroyView() {
        dismissLoadingDialog()
        super.onDestroyView()
    }

    companion object {
        private const val CONFIRM_CANCEL_MESSAGE= "표가 취소되었습니다."
    }
}