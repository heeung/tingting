package com.alsif.tingting.ui.reserve

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.databinding.FragmentHomeBinding
import com.alsif.tingting.databinding.FragmentReserveBinding
import com.alsif.tingting.ui.concertdetail.ConcertDetailFragment
import com.alsif.tingting.ui.concertdetail.ConcertDetailFragmentArgs
import com.alsif.tingting.ui.concertdetail.ConcertDetailFragmentViewModel
import com.alsif.tingting.ui.home.HomeFragmentViewModel
import com.alsif.tingting.ui.home.viewpager.AdsViewPagerAdapter
import com.alsif.tingting.ui.home.viewpager.TabViewPagerAdapter
import com.alsif.tingting.util.extension.setCurrentItemWithDuration
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "ReserveFragment"
@AndroidEntryPoint
class ReserveFragment : BaseFragment<FragmentReserveBinding>(FragmentReserveBinding::bind, com.alsif.tingting.R.layout.fragment_reserve) {

    private val viewModel : ReserveFragmentViewModel by viewModels()
    private val args: ReserveFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe()
        setClickListeners()
        Log.d(TAG, "onViewCreated: ${args.concertDetailSeq}, ${args.concertHallSeq}")
    }

    private fun setClickListeners() {
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun subscribe() {
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
    }
}