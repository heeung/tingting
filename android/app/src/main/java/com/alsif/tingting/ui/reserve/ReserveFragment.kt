package com.alsif.tingting.ui.reserve

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.databinding.FragmentHomeBinding
import com.alsif.tingting.databinding.FragmentReserveBinding
import com.alsif.tingting.ui.concertdetail.ConcertDetailFragment
import com.alsif.tingting.ui.concertdetail.ConcertDetailFragmentArgs
import com.alsif.tingting.ui.concertdetail.ConcertDetailFragmentViewModel
import com.alsif.tingting.ui.home.HomeFragmentViewModel
import com.alsif.tingting.ui.home.viewpager.AdsViewPagerAdapter
import com.alsif.tingting.ui.home.viewpager.TabViewPagerAdapter
import com.alsif.tingting.ui.reserve.recyclerview.SelectSeatAdapter
import com.alsif.tingting.ui.reserve.reservedepth.SectionFragment
import com.alsif.tingting.ui.reserve.reservedepth.SelectSeatFragment
import com.alsif.tingting.util.extension.setCurrentItemWithDuration
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "ReserveFragment"
@AndroidEntryPoint
class ReserveFragment : BaseFragment<FragmentReserveBinding>(FragmentReserveBinding::bind, R.layout.fragment_reserve) {

    private val viewModel : ReserveFragmentViewModel by viewModels()
    val args: ReserveFragmentArgs by navArgs()

    lateinit var seatListAdapter: SelectSeatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        subscribe()
        setClickListeners()
        changeFragment(SECTION_FRAGMENT_NAME)
        Log.d(TAG, "onViewCreated: ${args.concertDetailSeq}, ${args.concertHallSeq}")
    }

    private fun initRecyclerView() {
        seatListAdapter = SelectSeatAdapter()
        binding.recyclerSelectSeat.adapter = seatListAdapter
        binding.recyclerSelectSeat.layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerSelectSeat.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    private fun setClickListeners() {
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    fun changeFragment(name: String) {
        when(name) {
            SECTION_FRAGMENT_NAME -> {
                childFragmentManager.beginTransaction()
                    .replace(R.id.layout_reservation_frame, SectionFragment(viewModel, this))
                    .commit()
            }
            SELECT_SEAT_FRAGMENT_NAME -> {
                childFragmentManager.beginTransaction()
//                    .setCustomAnimations(
//                        R.anim.enter_from_right,
//                        R.anim.exit_to_left,
//                        R.anim.enter_from_left,
//                        R.anim.exit_to_right
//                    )
                    .replace(R.id.layout_reservation_frame, SelectSeatFragment(viewModel, this))
                    .addToBackStack(name)
                    .commit()
            }
            else -> {

            }
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
        private const val SECTION_FRAGMENT_NAME = "section"
        private const val SELECT_SEAT_FRAGMENT_NAME = "select_seat"
    }
}