package com.alsif.tingting.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.databinding.FragmentHomeBinding
import com.alsif.tingting.ui.home.viewpager.AdsViewPagerAdapter
import com.alsif.tingting.util.extension.setCurrentItemWithDuration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Math.ceil

private const val TAG = "HomeFragment"
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    private val viewModel: HomeFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdsPager()
    }

    private fun setClickListeners() {

    }

    private fun initAdsPager() {
        binding.adsPager.viewPager.adapter = AdsViewPagerAdapter(viewModel.pagerList)
        binding.adsPager.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.adsPager.txtCurrentBanner.text = getString(R.string.viewpager2_banner, 1, viewModel.pagerList.size)
        infiniteScrollPager()
    }

    private fun infiniteScrollPager() {
        binding.adsPager.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            //사용자가 스크롤 했을때 position 수정
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.bannerPosition = position

                binding.adsPager.txtCurrentBanner.text = getString(R.string.viewpager2_banner, (viewModel.bannerPosition % viewModel.pagerList.size) + 1, viewModel.pagerList.size)
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

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: 프레그먼트가 destroy 되었습니다.")
        super.onDestroy()
    }

    override fun onPause() {
        viewModel.job.cancel()
        super.onPause()
    }
}