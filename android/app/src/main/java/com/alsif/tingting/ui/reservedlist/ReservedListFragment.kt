package com.alsif.tingting.ui.reservedlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.databinding.FragmentHomeBinding
import com.alsif.tingting.databinding.FragmentLikedListBinding
import com.alsif.tingting.databinding.FragmentReservedListBinding
import com.alsif.tingting.ui.home.HomeFragmentViewModel
import com.alsif.tingting.ui.likedlist.LikedListFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservedListFragment : BaseFragment<FragmentReservedListBinding>(FragmentReservedListBinding::bind, R.layout.fragment_reserved_list) {
    private val viewModel: ReservedListFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}