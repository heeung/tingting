package com.alsif.tingting.ui.search

import android.annotation.SuppressLint
import android.os.Build
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
import com.alsif.tingting.ui.search.recyclerview.PageLoadingAdapter
import com.alsif.tingting.util.extension.parseLocalDateTime
import com.alsif.tingting.util.extension.parseLong
import com.alsif.tingting.util.extension.toDateString
import com.alsif.tingting.util.hideAnimation
import com.alsif.tingting.util.revealAnimation
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker.Builder.dateRangePicker
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.collect
import java.time.LocalDateTime


private const val TAG = "SearchFragment"
@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search) {
    private val viewModel: SearchFragmentViewModel by viewModels()
    private val sharedViewModel: MainActivityViewModel by activityViewModels()

    private lateinit var searchAdapter: SearchPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: viewCreated 되었습니다.")
        Log.d(TAG, "onViewCreated: ${viewModel.searchPagingDataFlow}")
        initRecyclerView()
        playSearchBarAnimation()
        setClickListeners()
        subscribe()
    }

    override fun onResume() {
        initMenu()
        super.onResume()
    }

    private fun getConcertList() {
//        showLoadingDialog(mActivity)
        val startDate = if (viewModel.isDateEditPossible.value && viewModel.isFilterOpened.value) viewModel.startDate.value.parseLocalDateTime().toDateString() else ""
        val endDate = if (viewModel.isDateEditPossible.value && viewModel.isFilterOpened.value) viewModel.endDate.value.parseLocalDateTime().toDateString() else ""
        val place = if (binding.textviewCitySelect.text.toString() != "지역" && viewModel.isFilterOpened.value) binding.textviewCitySelect.text.toString() else ""
        val searchWord = if (!binding.edittextSearch.text.isNullOrEmpty())  binding.edittextSearch.text.toString() else ""
        viewModel.getConcertList(ConcertListRequestDto(INITIAL_PAGE_NUM, PAGE_SIZE, "", startDate, endDate, place, searchWord))
    }

    private fun getConcertListWithLoadingDialog() {
        showLoadingDialog()
        val startDate = if (viewModel.isDateEditPossible.value && viewModel.isFilterOpened.value) viewModel.startDate.value.parseLocalDateTime().toDateString() else ""
        val endDate = if (viewModel.isDateEditPossible.value && viewModel.isFilterOpened.value) viewModel.endDate.value.parseLocalDateTime().toDateString() else ""
        val place = if (binding.textviewCitySelect.text.toString() != "지역" && viewModel.isFilterOpened.value) binding.textviewCitySelect.text.toString() else ""
        val searchWord = if (!binding.edittextSearch.text.isNullOrEmpty())  binding.edittextSearch.text.toString() else ""
        viewModel.getConcertList(ConcertListRequestDto(INITIAL_PAGE_NUM, PAGE_SIZE, "", startDate, endDate, place, searchWord))
    }

    @SuppressLint("ResourceAsColor")
    private fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collectLatest {
                binding.layoutSwipeRefresh.isRefreshing = false
                dismissLoadingDialog()
                showToast(it.message.toString())
            }
        }
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
                    binding.buttonFilter.setBackgroundResource(R.drawable.frame_button_filter_off)
                    binding.buttonFilter.setImageResource(R.drawable.ic_filter_white)
                } else {
                    binding.layoutFilter.apply {
                        translateAnimation(AnimUtil.AnimDirection.Y, -200f , AnimUtil.Speed.COMMON)
                    }
                    binding.buttonFilter.setBackgroundResource(R.drawable.frame_button_filter_on)
                    binding.buttonFilter.setImageResource(R.drawable.ic_filter)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.startDate.collect {
                binding.textviewStartDate.setText(it.parseLocalDateTime().toDateString())
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.endDate.collect {
                binding.textviewEndDate.setText(it.parseLocalDateTime().toDateString())
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isDateEditPossible.collect {
                if (it) {
                    binding.boxStartDate.revealAnimation(viewLifecycleOwner)
                    binding.boxEndDate.revealAnimation(viewLifecycleOwner)
//                    binding.boxStartDate.visibility = View.VISIBLE
//                    binding.boxEndDate.visibility = View.VISIBLE
                    binding.layoutDateRange.isClickable = true
                    binding.buttonDatePicker.setBackgroundResource(R.drawable.frame_button_date_picker_off)
                    binding.imageDatePicker.setImageResource(R.drawable.ic_calendar_white)
                } else {
                    binding.boxStartDate.hideAnimation(viewLifecycleOwner)
                    binding.boxEndDate.hideAnimation(viewLifecycleOwner)
//                    binding.boxStartDate.visibility = View.INVISIBLE
//                    binding.boxEndDate.visibility = View.INVISIBLE
                    binding.layoutDateRange.isClickable = false
                    binding.buttonDatePicker.setBackgroundResource(R.drawable.frame_button_date_picker_on)
                    binding.imageDatePicker.setImageResource(R.drawable.ic_calendar)
                }
            }
        }
        // 검색 결과에 따라 보여주기
        searchAdapter.addOnPagesUpdatedListener {
            dismissLoadingDialog()
            binding.layoutSwipeRefresh.isRefreshing = false
            if (searchAdapter.itemCount == 0) {
                binding.buttonScrollUp.visibility = View.GONE
                showSnackbar(binding.root, NEED_SEARCH_RE)
            } else {
                binding.buttonScrollUp.visibility = View.VISIBLE
            }
        }
    }

    private fun initRecyclerView() {
        searchAdapter = SearchPagingAdapter()
        binding.recyclerSearch.apply {
            adapter = searchAdapter.withLoadStateFooter(
                footer = PageLoadingAdapter()
            )
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
            it.clickAnimation(viewLifecycleOwner)
            getConcertListWithLoadingDialog()
        }
        binding.edittextSearch.setOnEditorActionListener(OnEditorActionListener { view, actionId, _ ->
            when (actionId) {
                IME_ACTION_SEARCH -> {
                    getConcertListWithLoadingDialog()
                    hideKeyBoard(view)
                }
            }
            true
        })
        binding.buttonFilter.setOnClickListener {
            it.clickAnimation(viewLifecycleOwner)
            viewModel.toggleFilter()
        }
        binding.layoutDateRange.setOnClickListener {
            setDateRangePicker()
        }
        binding.textviewStartDate.setOnClickListener {
            setDateRangePicker()
        }
        binding.textviewEndDate.setOnClickListener {
            setDateRangePicker()
        }
        binding.buttonDatePicker.setOnClickListener {
            it.clickAnimation(viewLifecycleOwner)
            viewModel.toggleDateEdit()
        }
        binding.textviewCitySelect.setOnItemClickListener { _, _, _, _ ->
            getConcertListWithLoadingDialog()
        }
        binding.layoutSwipeRefresh.setOnRefreshListener {
            getConcertList()
        }
    }

    private fun setDateRangePicker() {
        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText(DATE_PICKER_TITLE)
                .setSelection(
                    Pair(
                        viewModel.startDate.value,
                        viewModel.endDate.value
                    )
                )
                .setTheme(R.style.CustomDateRangePicker)
                .setPositiveButtonText(CONFIRM)
                .build()
        dateRangePicker.show(childFragmentManager, "date_picker")
        dateRangePicker.addOnPositiveButtonClickListener {
//            binding.textviewStartDate.setText(dateRangePicker.selection?.first?.parseLocalDateTime()?.toDateString())
//            binding.textviewEndDate.setText(dateRangePicker.selection?.second?.parseLocalDateTime()?.toDateString())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                viewModel.setStartDate(dateRangePicker.selection?.first ?: LocalDateTime.MIN.parseLong())
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                viewModel.setEndDate(dateRangePicker.selection?.second ?: LocalDateTime.MAX.parseLong())
            }
            getConcertListWithLoadingDialog()
        }
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
        private const val INITIAL_PAGE_NUM = 1
        private const val PAGE_SIZE = 10
        private const val DATE_PICKER_TITLE = "공연 기간을 골라주세요"
        private const val CONFIRM = "확인"
        private const val NEED_SEARCH_RE = "검색된 공연이 없어요! 다시 검색해주세요."
    }
}