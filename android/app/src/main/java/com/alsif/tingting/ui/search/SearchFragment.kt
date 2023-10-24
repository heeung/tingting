package com.alsif.tingting.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.databinding.FragmentHomeBinding
import com.alsif.tingting.databinding.FragmentLikedListBinding
import com.alsif.tingting.databinding.FragmentReservedListBinding
import com.alsif.tingting.databinding.FragmentSearchBinding
import com.alsif.tingting.ui.home.HomeFragmentViewModel
import com.alsif.tingting.ui.likedlist.LikedListFragmentViewModel
import com.alsif.tingting.ui.reservedlist.ReservedListFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "SearchFragment"
@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search) {
    private val viewModel: SearchFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        focusAndShowKeyBoard(binding.edittextSearch)
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.layoutAppbar.setOnClickListener {
            focusAndShowKeyBoard(binding.edittextSearch)
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: 프레그먼트가 destroy 되었습니다.")
        super.onDestroy()
    }
}