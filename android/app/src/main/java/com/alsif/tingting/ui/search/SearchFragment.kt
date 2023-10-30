package com.alsif.tingting.ui.search

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.request.ConcertListRequestDto
import com.alsif.tingting.databinding.FragmentHomeBinding
import com.alsif.tingting.databinding.FragmentLikedListBinding
import com.alsif.tingting.databinding.FragmentReservedListBinding
import com.alsif.tingting.databinding.FragmentSearchBinding
import com.alsif.tingting.ui.concertdetail.ConcertDetailFragmentArgs
import com.alsif.tingting.ui.home.HomeFragmentDirections
import com.alsif.tingting.ui.home.HomeFragmentViewModel
import com.alsif.tingting.ui.home.tab.recyclerview.ConcertPagingAdapter
import com.alsif.tingting.ui.likedlist.LikedListFragmentViewModel
import com.alsif.tingting.ui.login.LoginModalBottomSheet
import com.alsif.tingting.ui.main.MainActivityViewModel
import com.alsif.tingting.ui.reservedlist.ReservedListFragmentViewModel
import com.alsif.tingting.ui.search.recyclerview.SearchPagingAdapter
import com.alsif.tingting.util.AnimUtil
import com.alsif.tingting.util.clickAnimation
import com.alsif.tingting.util.scaleAnimation
import com.alsif.tingting.util.translateAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "SearchFragment"
@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search) {
    private val viewModel: SearchFragmentViewModel by viewModels()
    private val sharedViewModel: MainActivityViewModel by activityViewModels()

    private lateinit var searchAdapter: SearchPagingAdapter
    lateinit var datePickerBottomSheet: DatePickerBottomSheet

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: viewCreated 되었습니다.")
        Log.d(TAG, "onViewCreated: ${viewModel.searchPagingDataFlow}")
        initRecyclerView()
        playSearchBarAnimation()
        setClickListeners()
        subscribe()
        getConcertList()
        showDatePickerBottomSheet()
    }

    private fun getConcertList() {
        // 아무것도 없는 호출
//        viewModel.getConcertList(ConcertListRequestDto(1, 10, "", "", "", "", ""))
//        // 검색어만 있는 호출
//        viewModel.getConcertList(ConcertListRequestDto(1, 10, "", "", "", "", "화려한"))
//        // 장소 + 검색어
//        viewModel.getConcertList(ConcertListRequestDto(1, 10, "", "", "", "서울", "화려한"))
//          // 기간 + 검색어
//        viewModel.getConcertList(ConcertListRequestDto(1, 10, "", "2023-12-01", "2023-12-10", "", "화려한"))
//        // 기간 + 장소 + 검색어
//        viewModel.getConcertList(ConcertListRequestDto(1, 10, "", "2023-12-01", "2023-12-10", "서울", "화려한"))
//        // 장소만
//        viewModel.getConcertList(ConcertListRequestDto(1, 10, "", "", "", "서울", ""))
//        // 기간만
//        viewModel.getConcertList(ConcertListRequestDto(1, 10, "", "2023-12-01", "2023-12-10", "", ""))
//        // 장소 + 기간만
//        viewModel.getConcertList(ConcertListRequestDto(1, 10, "", "2023-12-01", "2023-12-10", "서울", ""))
    }

    private fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchPagingDataFlow.collectLatest { it ->
                searchAdapter.submitData(it)
            }
        }
    }

    private fun initRecyclerView() {
        searchAdapter = SearchPagingAdapter()
        binding.recyclerSearch.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(mActivity, 2, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun playSearchBarAnimation() {
        binding.layoutSearchBar.apply {
            scaleX = SEARCH_BAR_INITIAL_SIZE
            translationX = SEARCH_BAR_INITIAL_TRANSLATION_X
            scaleAnimation(AnimUtil.AnimDirection.X, AnimUtil.Size.ORIGIN, AnimUtil.Speed.FAST)
            translateAnimation(AnimUtil.AnimDirection.X, AnimUtil.Size.MIN, AnimUtil.Speed.FAST)
        }
    }

    private fun setClickListeners() {
        binding.layoutAppbar.setOnClickListener {
            focusAndShowKeyBoard(binding.edittextSearch)
        }
        searchAdapter.itemClickListner = object: SearchPagingAdapter.ItemClickListener {
            override fun onClick(view: View, concert: ConcertDto) {
                val action = SearchFragmentDirections.actionSearchFragmentToConcertDetailFragment(concert.concertSeq)
                findNavController().navigate(action)
            }
        }
        binding.buttonScrollUp.setOnClickListener {
            binding.buttonScrollUp.clickAnimation(viewLifecycleOwner)

            val smoothScroller = object : LinearSmoothScroller(binding.recyclerSearch.context) {
                override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                    return 30f / displayMetrics.densityDpi
                }
            }
            smoothScroller.targetPosition = 0

            val scrollManager = binding.recyclerSearch.layoutManager as LinearLayoutManager
            if (scrollManager.findFirstCompletelyVisibleItemPosition() > 10) {
                binding.recyclerSearch.scrollToPosition(10)
                scrollManager.startSmoothScroll(smoothScroller)
            } else {
                scrollManager.startSmoothScroll(smoothScroller)
            }
        }
        binding.buttonSearch.setOnClickListener {
            viewModel.getConcertList(ConcertListRequestDto(1, 10, "", "", "", "", binding.edittextSearch.text.toString()))
        }
    }

    private fun showDatePickerBottomSheet() {
        datePickerBottomSheet = DatePickerBottomSheet()
        datePickerBottomSheet.show(childFragmentManager, LoginModalBottomSheet.TAG)
    }

    private fun dismissDatePickerBottomSheet() {
        datePickerBottomSheet.dismiss()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView: 프레그먼트가 destroyView 되었습니다.")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: 프레그먼트가 destroy 되었습니다.")
        super.onDestroy()
    }

    companion object {
        private const val SEARCH_BAR_INITIAL_SIZE = 0.9f
        private const val SEARCH_BAR_INITIAL_TRANSLATION_X = 50f
    }
}