package com.alsif.tingting.ui.home.tab

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.request.ConcertListRequestDto
import com.alsif.tingting.databinding.FragmentSoonSaleListBinding
import com.alsif.tingting.ui.home.HomeFragmentViewModel
import com.alsif.tingting.ui.home.tab.recyclerview.ConcertPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "SoonSaleListFragment"
@AndroidEntryPoint
class SoonSaleListFragment: BaseFragment<FragmentSoonSaleListBinding>(FragmentSoonSaleListBinding::bind, R.layout.fragment_soon_sale_list) {

    private val viewModel: HomeFragmentViewModel by viewModels()
    private lateinit var concertAdapter: ConcertPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        subscribe()
//        getConcertList()
//        binding.recyclerSoonSale.smoothScrollToPosition(0)
    }

    override fun onResume() {
        super.onResume()
        getConcertList()
        binding.recyclerSoonSale.smoothScrollToPosition(0)
    }

    private fun getConcertList() {
        //TODO 호출 바꾸기
        viewModel.getSoonSaleConcertList(ConcertListRequestDto(1, 10, "soon", "", "", "", ""))
    }

    private fun initRecyclerView() {
        concertAdapter = ConcertPagingAdapter()
        binding.recyclerSoonSale.apply {
            adapter = concertAdapter
            layoutManager = GridLayoutManager(mActivity, 2, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.soonSalePagingDataFlow.collect { it ->
                concertAdapter.submitData(it)
            }
        }
    }
}