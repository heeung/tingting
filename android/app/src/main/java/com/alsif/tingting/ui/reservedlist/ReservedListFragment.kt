package com.alsif.tingting.ui.reservedlist

import android.annotation.SuppressLint
import android.app.ProgressDialog.show
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.TicketDto
import com.alsif.tingting.databinding.FragmentHomeBinding
import com.alsif.tingting.databinding.FragmentLikedListBinding
import com.alsif.tingting.databinding.FragmentReservedListBinding
import com.alsif.tingting.ui.home.HomeFragmentViewModel
import com.alsif.tingting.ui.likedlist.LikedListFragment
import com.alsif.tingting.ui.likedlist.LikedListFragmentDirections
import com.alsif.tingting.ui.likedlist.LikedListFragmentViewModel
import com.alsif.tingting.ui.likedlist.recyclerview.LikedListPagingAdapter
import com.alsif.tingting.ui.likedlist.recyclerview.ReservedListPagingAdapter
import com.alsif.tingting.ui.login.LoginModalBottomSheet
import com.alsif.tingting.ui.main.MainActivityViewModel
import com.alsif.tingting.ui.search.recyclerview.PageLoadingAdapter
import com.alsif.tingting.util.AnimUtil
import com.alsif.tingting.util.clickAnimation
import com.alsif.tingting.util.scaleAnimation
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "ReservedListFragment"
@AndroidEntryPoint
class ReservedListFragment : BaseFragment<FragmentReservedListBinding>(FragmentReservedListBinding::bind, R.layout.fragment_reserved_list) {
    private val viewModel: ReservedListFragmentViewModel by viewModels()
    private val sharedViewModel: MainActivityViewModel by activityViewModels()

    private lateinit var reservedListAdapter: ReservedListPagingAdapter

    private val ticketDetailBottomSheet by lazy {
         TicketDetailModalBottomSheet()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (mActivity.requireLogin()) {
            initRecyclerView()
            subscribe()
            setClickListeners()
            getReservedListWithLoadingDialog()
        }
    }

    override fun onResume() {
        super.onResume()
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
                showTicketDetailBottomSheet()
//                val action = ReservedListFragmentDirections.actionReservedListFragmentToConcertDetailFragment(ticketDto.concertSeq)
//                findNavController().navigate(action)

            }
        }
        binding.layoutSwipeRefresh.setOnRefreshListener {
            getReservedList()
        }
        // 검색 결과에 따라 보여주기
        reservedListAdapter.addOnPagesUpdatedListener {
            dismissLoadingDialog()
            binding.layoutSwipeRefresh.isRefreshing = false
//            if (reservedListAdapter.itemCount == 0) {
//                showSnackbar(binding.root, LikedListFragment.NEED_LIKE_MESSAGE)
//            }
        }
    }

    fun showTicketDetailBottomSheet() {
        ticketDetailBottomSheet.show(childFragmentManager, LoginModalBottomSheet.TAG)
    }

    fun dismissTicketDetailBottomSheet() {
        ticketDetailBottomSheet.dismiss()
    }

    override fun onDestroyView() {
        dismissLoadingDialog()
        super.onDestroyView()
    }
}