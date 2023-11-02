package com.alsif.tingting.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.data.model.request.ConcertListRequestDto
import com.alsif.tingting.databinding.FragmentHomeBinding
import com.alsif.tingting.ui.home.viewpager.AdsViewPagerAdapter
import com.alsif.tingting.ui.home.viewpager.TabViewPagerAdapter
import com.alsif.tingting.util.extension.setCurrentItemWithDuration
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Math.ceil


private const val TAG = "HomeFragment"
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, com.alsif.tingting.R.layout.fragment_home) {

    private val viewModel: HomeFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe()
        initAdsPager()
        initTabLayout()
    }

    private fun setClickListeners() {

    }

    private fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collectLatest {
                dismissLoadingDialog()
                mActivity.showToast(it.message.toString())
            }
        }
    }

    private fun initTabLayout() {
        // 탭과 뷰페이저 연결
        val tabLayout = binding.tabCategory
        val tabLayoutMediator = TabLayoutMediator(tabLayout, tabPager()) { tab, position ->
            if (position == 0) {
                tab.text = ON_SALE
            } else {
                tab.text = SOON_SALE
            }
        }
        tabLayoutMediator.attach()
    }

    private fun tabPager(): ViewPager2 {
        val viewPager2 = binding.viewpagerCategory
        viewPager2.adapter = TabViewPagerAdapter(this)
        viewPager2.currentItem = 0
        return viewPager2
    }

    private fun initAdsPager() {
        binding.adsPager.viewPager.adapter = AdsViewPagerAdapter(viewModel.pagerList)
        binding.adsPager.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.adsPager.txtCurrentBanner.text = getString(com.alsif.tingting.R.string.viewpager2_banner, 1, viewModel.pagerList.size)
        infiniteScrollPager()
    }

    private fun infiniteScrollPager() {
        binding.adsPager.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            //사용자가 스크롤 했을때 position 수정
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.bannerPosition = position

                binding.adsPager.txtCurrentBanner.text = getString(com.alsif.tingting.R.string.viewpager2_banner, (viewModel.bannerPosition % viewModel.pagerList.size) + 1, viewModel.pagerList.size)
            }
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                when (state) {
                    ViewPager2.SCROLL_STATE_IDLE ->{
                        if (!viewModel.job.isActive) scrollJobCreate()
                    }
                    ViewPager2.SCROLL_STATE_DRAGGING -> viewModel.job.cancel()

                    ViewPager2.SCROLL_STATE_SETTLING -> {}
                }
            }
        })

        viewModel.bannerPosition = Int.MAX_VALUE / 2 - ceil(viewModel.pagerList.size.toDouble() / 2).toInt()
        // TODO 다른 frament갔다오면 처음으로 돌아가는 현상 발생
        binding.adsPager.viewPager.setCurrentItem(viewModel.bannerPosition, false)
    }

    private fun scrollJobCreate() {
        viewModel.job = lifecycleScope.launch {
            delay(2500)
            Log.d(TAG, "scrollJobCreate: 광고 배너 포지션 ${viewModel.bannerPosition}")
            binding.adsPager.viewPager.setCurrentItemWithDuration(++viewModel.bannerPosition, 500)
        }
    }

    override fun onResume() {
        scrollJobCreate()
        super.onResume()
    }

    override fun onDestroyView() {
        dismissLoadingDialog()
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: 프레그먼트가 destroy 되었습니다.")
        super.onDestroy()
    }

    override fun onPause() {
        viewModel.job.cancel()
        super.onPause()
    }

    companion object {
        private const val ON_SALE = "예매 중"
        private const val SOON_SALE = "예매 임박"
    }
}