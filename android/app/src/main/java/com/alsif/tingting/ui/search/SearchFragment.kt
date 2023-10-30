package com.alsif.tingting.ui.search

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.widget.ArrayAdapter
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.request.ConcertListRequestDto
import com.alsif.tingting.databinding.FragmentSearchBinding
import com.alsif.tingting.ui.login.LoginModalBottomSheet
import com.alsif.tingting.ui.main.MainActivityViewModel
import com.alsif.tingting.ui.search.recyclerview.SearchPagingAdapter
import com.alsif.tingting.util.AnimUtil
import com.alsif.tingting.util.clickAnimation
import com.alsif.tingting.util.scaleAnimation
import com.alsif.tingting.util.translateAnimation
import com.google.android.material.datepicker.MaterialDatePicker
import com.kakao.sdk.friend.m.v
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.core.util.Pair
import com.google.android.material.datepicker.MaterialDatePicker.Builder.dateRangePicker


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
        initMenu()
//        showDatePickerBottomSheet()
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isFilterOpened.collect {
                if (it) {
                    binding.layoutFilter.apply {
                        translateAnimation(AnimUtil.AnimDirection.Y, 0f , AnimUtil.Speed.COMMON)
                    }
                } else {
                    binding.layoutFilter.apply {
                        translateAnimation(AnimUtil.AnimDirection.Y, -200f , AnimUtil.Speed.COMMON)
                    }
                }
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

    private fun initMenu() {
        val regionArray = resources.getStringArray(R.array.select_region)
        val arrayAdapter = ArrayAdapter(mActivity, R.layout.drop_down_item, regionArray)
        binding.textviewCitySelect.setDropDownBackgroundResource(R.color.white)
        binding.textviewCitySelect.setAdapter(arrayAdapter)
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
        binding.edittextSearch.setOnEditorActionListener(OnEditorActionListener { view, actionId, _ ->
            when (actionId) {
                IME_ACTION_SEARCH -> {
                    viewModel.getConcertList(ConcertListRequestDto(1, 10, "", "", "", "", binding.edittextSearch.text.toString()))
                    hideKeyBoard(view)
                }
            }
            true
        })
        binding.buttonFilter.setOnClickListener {
            it.clickAnimation(viewLifecycleOwner)
            viewModel.toggleFilter()
        }
        binding.buttonDatePicker.setOnClickListener {
            val dateRangePicker =
                MaterialDatePicker.Builder.dateRangePicker()
                    .setTitleText("공연 기간을 골라주세요")
                    .setSelection(
                        Pair(
                            MaterialDatePicker.thisMonthInUtcMilliseconds(),
                            MaterialDatePicker.todayInUtcMilliseconds()
                        )
                    )
                    .build()
            dateRangePicker.show(childFragmentManager, "date_picker")
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