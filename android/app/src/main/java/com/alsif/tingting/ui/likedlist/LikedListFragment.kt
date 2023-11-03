package com.alsif.tingting.ui.likedlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.databinding.FragmentHomeBinding
import com.alsif.tingting.databinding.FragmentLikedListBinding
import com.alsif.tingting.ui.home.HomeFragmentDirections
import com.alsif.tingting.ui.home.HomeFragmentViewModel
import com.alsif.tingting.ui.home.tab.recyclerview.ConcertPagingAdapter
import com.alsif.tingting.ui.likedlist.recyclerview.LikedListPagingAdapter
import com.alsif.tingting.ui.login.LoginModalBottomSheet
import com.alsif.tingting.ui.main.MainActivity
import com.alsif.tingting.ui.main.MainActivityViewModel
import com.alsif.tingting.ui.search.recyclerview.PageLoadingAdapter
import com.alsif.tingting.util.clickAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "LikedListFragment"
@AndroidEntryPoint
class LikedListFragment : BaseFragment<FragmentLikedListBinding>(FragmentLikedListBinding::bind, R.layout.fragment_liked_list) {
    private val viewModel: LikedListFragmentViewModel by viewModels()
    private val sharedViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var likedListAdapter: LikedListPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: 뷰 붙음")
        
        if (mActivity.requireLogin()) {
            initRecyclerView()
            subscribe()
            setClickListeners()
//            getLikedConcertListWithLoadingDialog()
        }
    }

    override fun onResume() {
        getLikedConcertListWithLoadingDialog()
        super.onResume()
    }

    private fun getLikedConcertList() {
        viewModel.getLikedConcertList()
    }

    private fun getLikedConcertListWithLoadingDialog() {
        showLoadingDialog()
        viewModel.getLikedConcertList()
    }

    private fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.likedListPagingDataFlow.collectLatest {
                likedListAdapter.submitData(it)
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
        likedListAdapter = LikedListPagingAdapter()
        binding.recyclerLikedList.apply {
            adapter = likedListAdapter.withLoadStateFooter(
                footer = PageLoadingAdapter()
            )
            layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setClickListeners() {
        likedListAdapter.itemClickListner = object: LikedListPagingAdapter.ItemClickListener {
            override fun onClick(view: View, concert: ConcertDto) {
                val action = LikedListFragmentDirections.actionLikedListFragmentToConcertDetailFragment(concert.concertSeq)
                findNavController().navigate(action)
            }
        }
        binding.layoutSwipeRefresh.setOnRefreshListener {
            getLikedConcertList()
        }
        // 검색 결과에 따라 보여주기
        likedListAdapter.addOnPagesUpdatedListener {
            dismissLoadingDialog()
            binding.layoutSwipeRefresh.isRefreshing = false
            if (likedListAdapter.itemCount == 0) {
                showSnackbar(binding.root, NEED_LIKE_MESSAGE)
            }
        }
    }

    override fun onDestroyView() {
        dismissLoadingDialog()
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private const val NEED_LIKE_MESSAGE = "공연을 찜해놓으면 편하게 볼 수 있어요 :)"
    }
}