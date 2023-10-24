package com.alsif.tingting.ui.home.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alsif.tingting.databinding.FragmentOnSaleListBinding
import com.alsif.tingting.ui.home.tab.OnSaleListFragment
import com.alsif.tingting.ui.home.tab.SoonSaleListFragment

class TabViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return TAB_LAYOUT_COUNT
    }

    // 포지션에 따라 레이아웃 구성
    override fun createFragment(position: Int): Fragment {
        // TODO 아마 그냥 인스턴스 생성하고 그거 넣어야 할듯
        // TODO 이렇게 하면 계속 인스턴스 생성하고 리턴해서 메모리상 안좋을듯
        return when (position) {
            0 -> {
                OnSaleListFragment()
            }
            else -> {
                SoonSaleListFragment()
            }
        }
    }

    companion object {
        private const val TAB_LAYOUT_COUNT = 2
    }
}