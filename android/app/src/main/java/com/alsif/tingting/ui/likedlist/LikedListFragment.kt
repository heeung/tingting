package com.alsif.tingting.ui.likedlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.databinding.FragmentHomeBinding
import com.alsif.tingting.databinding.FragmentLikedListBinding
import com.alsif.tingting.ui.home.HomeFragmentViewModel
import com.alsif.tingting.ui.login.LoginModalBottomSheet
import com.alsif.tingting.ui.main.MainActivity
import com.alsif.tingting.ui.main.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikedListFragment : BaseFragment<FragmentLikedListBinding>(FragmentLikedListBinding::bind, R.layout.fragment_liked_list) {
    private val viewModel: LikedListFragmentViewModel by viewModels()
    private val sharedViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mActivity.requireLogin()
    }
}