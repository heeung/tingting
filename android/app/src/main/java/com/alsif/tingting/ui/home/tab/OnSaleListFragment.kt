package com.alsif.tingting.ui.home.tab

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.request.ConcertListRequestDto
import com.alsif.tingting.databinding.FragmentOnSaleListBinding
import com.alsif.tingting.ui.home.HomeFragmentViewModel
import com.alsif.tingting.ui.home.tab.recyclerview.ConcertPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "OnSaleListFragment"
@AndroidEntryPoint
class OnSaleListFragment: BaseFragment<FragmentOnSaleListBinding>(FragmentOnSaleListBinding::bind, R.layout.fragment_on_sale_list) {

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
        binding.recyclerOnSale.smoothScrollToPosition(0)
    }

    private fun getConcertList() {
        //TODO 호출 바꾸기
        viewModel.getOnSaleConcertList(ConcertListRequestDto(1, 10, "now", "", "", "", ""))
    }

    private fun initRecyclerView() {
        concertAdapter = ConcertPagingAdapter()
        binding.recyclerOnSale.apply {
            adapter = concertAdapter
            layoutManager = GridLayoutManager(mActivity, 2, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.onSalePagingDataFlow.collect { it ->
                concertAdapter.submitData(it)
            }
        }
    }
}