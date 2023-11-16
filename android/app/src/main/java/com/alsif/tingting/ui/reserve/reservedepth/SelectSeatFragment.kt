package com.alsif.tingting.ui.reserve.reservedepth

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.forEach
import androidx.lifecycle.lifecycleScope
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.data.model.SeatSelectionDto
import com.alsif.tingting.databinding.FragmentSelectSeatBinding
import com.alsif.tingting.ui.reserve.ReserveFragment
import com.alsif.tingting.ui.reserve.ReserveFragmentViewModel
import com.alsif.tingting.ui.reserve.recyclerview.SelectSeatListAdapter
import com.kakao.sdk.friend.m.v
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Package.getPackage
import java.util.Arrays
import java.util.Collections
import javax.inject.Inject
import kotlin.text.Typography.section

private const val TAG = "SelectSeatFragment"
@AndroidEntryPoint
class SelectSeatFragment @Inject constructor(
    private val viewModel : ReserveFragmentViewModel,
    private val mFragment: ReserveFragment,
    private val section: String
) : BaseFragment<FragmentSelectSeatBinding>(FragmentSelectSeatBinding::bind, com.alsif.tingting.R.layout.fragment_select_seat) {

//    private var resList: Array<Array<Int>> = Array(10) { Array(21) { 0 } }
    private var mapStoI: HashMap<String, Int> = hashMapOf()
    private var mapViewToSeat: HashMap<View, SeatSelectionDto> = hashMapOf()
    private var list: MutableList<SeatSelectionDto> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe()
        setClickListeners()
        binding.textviewSection.text = section
    }

    private fun setClickListeners() {
        binding.A.forEach {
            it.onSeatClick()
        }
        binding.B.forEach {
            it.onSeatClick()
        }
        binding.C.forEach {
            it.onSeatClick()
        }
        binding.D.forEach {
            it.onSeatClick()
        }
        binding.E.forEach {
            it.onSeatClick()
        }
        binding.F.forEach {
            it.onSeatClick()
        }
        binding.G.forEach {
            it.onSeatClick()
        }
        binding.H.forEach {
            it.onSeatClick()
        }
        binding.I.forEach {
            it.onSeatClick()
        }
        binding.J.forEach {
            it.onSeatClick()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun View.onSeatClick() {
        this.setOnClickListener {
//            Log.d(TAG, "onSeatClick: ${mapViewToSeat[it]!!}")
            if (mapViewToSeat[it]!!.book) {
                it.setBackgroundResource(R.drawable.frame_seat_possible)
                viewModel.unSelectSeat(mapViewToSeat[it]!!)
                mapViewToSeat[it]!!.book = false
            } else {
                it.setBackgroundResource(R.drawable.frame_seat_selected)
                viewModel.selectSeat(mapViewToSeat[it]!!)
                mapViewToSeat[it]!!.book = true
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun subscribe() {
        // TODO 데이터 갱신 차이 문제,,
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.seatList.collectLatest {
                it.forEach { item ->
                    val viewId = resources.getIdentifier(item.seat, "id", "com.alsif.tingting")
                    val v = binding.root.findViewById<View>(viewId)
                    mapStoI[item.seat] = viewId
                    mapViewToSeat[v] = item

                    when (item.book) {
                        true -> {
                            v.isClickable = false
                            v.isEnabled = false
                        }
                        false -> {
                            v.isClickable = true
                            v.isEnabled = true
                            v.setBackgroundResource(R.drawable.frame_seat_possible)
                        }
                    }
                }
                mFragment.dismissLoadingDialog()
            }
        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.selectList.collectLatest {
//                for(d in it) {
//                    Log.d(TAG, "selectSeat: $d")
//                }
//            }
//        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectListSize.collectLatest {
                for (a in viewModel.selectListList.value) {
                    Log.d(TAG, "selectSeat: $a")
                }
                mFragment.initRecyclerView()
            }
        }
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