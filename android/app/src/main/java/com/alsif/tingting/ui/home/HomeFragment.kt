package com.alsif.tingting.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.data.model.PagerDataDto
import com.alsif.tingting.databinding.FragmentHomeBinding
import com.alsif.tingting.ui.home.dump.pagerList
import com.alsif.tingting.ui.home.viewpager.AdsViewPagerAdapter
import com.alsif.tingting.util.extension.setCurrentItemWithDuration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import java.lang.Math.ceil

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {
    private val viewModel: HomeFragmentViewModel by viewModels()
    ///////////
    private var bannerPosition = 0
    lateinit var job : Job
    ///////////

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list: ArrayList<PagerDataDto> = ArrayList<PagerDataDto>().let {
            it.apply {
                add(PagerDataDto(android.R.color.holo_red_light, "1 Page", pagerList[0]))
                add(PagerDataDto(android.R.color.holo_orange_dark, "2 Page",pagerList[1]))
                add(PagerDataDto(android.R.color.holo_green_dark, "3 Page", pagerList[2]))
                add(PagerDataDto(android.R.color.holo_blue_light, "4 Page", pagerList[3]))
                add(PagerDataDto(android.R.color.holo_blue_bright, "5 Page", pagerList[4]))
            }
        }

        binding.adsPager.viewPager.adapter = AdsViewPagerAdapter(list)
        binding.adsPager.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.adsPager.txtCurrentBanner.text = getString(R.string.viewpager2_banner, 1, list.size)

        // 무한 스크롤 구혀
        binding.adsPager.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            //사용자가 스크롤 했을때 position 수정
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bannerPosition = position

                binding.adsPager.txtCurrentBanner.text = getString(R.string.viewpager2_banner, (bannerPosition % list.size)+1, list.size)
            }
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                when (state) {
                    ViewPager2.SCROLL_STATE_IDLE ->{
                        if (!job.isActive) scrollJobCreate()
                    }
                    ViewPager2.SCROLL_STATE_DRAGGING -> job.cancel()

                    ViewPager2.SCROLL_STATE_SETTLING -> {}
                }
            }
        })

        bannerPosition = Int.MAX_VALUE / 2 - ceil(list.size.toDouble() / 2).toInt()
        binding.adsPager.viewPager.setCurrentItem(bannerPosition, false)
    }

    fun scrollJobCreate() {
        job = lifecycleScope.launchWhenResumed {
            delay(2500)
            binding.adsPager.viewPager.setCurrentItemWithDuration(++bannerPosition, 500)
        }
    }

    override fun onResume() {
        super.onResume()
        scrollJobCreate()
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
    }
}