package com.alsif.tingting.ui.reservedlist

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
import com.alsif.tingting.databinding.FragmentReservedListBinding
import com.alsif.tingting.ui.home.HomeFragmentViewModel
import com.alsif.tingting.ui.likedlist.LikedListFragmentViewModel
import com.alsif.tingting.ui.main.MainActivityViewModel
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservedListFragment : BaseFragment<FragmentReservedListBinding>(FragmentReservedListBinding::bind, R.layout.fragment_reserved_list) {
    private val viewModel: ReservedListFragmentViewModel by viewModels()
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
        binding.btnLogout.setOnClickListener {
            UserApiClient.instance.logout {
                sharedViewModel.resetToken()
                showToast("로그아웃 되었습니다.")
                // TODO 홈으로 가기
            }
        }
    }
}