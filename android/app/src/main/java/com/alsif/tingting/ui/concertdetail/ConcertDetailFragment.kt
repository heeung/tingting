package com.alsif.tingting.ui.concertdetail

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.databinding.FragmentConcertDetailBinding
import com.alsif.tingting.ui.home.HomeFragmentViewModel
import com.alsif.tingting.ui.search.SearchFragment
import com.alsif.tingting.util.AnimUtil
import com.alsif.tingting.util.clickAnimation
import com.alsif.tingting.util.extension.setStatusBarTransparent
import com.alsif.tingting.util.setScaleXY
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConcertDetailFragment: BaseFragment<FragmentConcertDetailBinding>(FragmentConcertDetailBinding::bind, R.layout.fragment_concert_detail) {

    private val viewModel: ConcertDetailFragmentViewModel by viewModels()
    private val args: ConcertDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe()
        setClickListeners()
        getConcertDetail()
    }

    private fun getConcertDetail() {
        viewModel.getConcertDetail(args.concertSeq, TEST_USERSEQ)
    }

    private fun setClickListeners() {
        binding.buttonLike.setOnClickListener {
            toggleLikeButton(binding.textviewButtonLike.text.toString())
            it.clickAnimation(viewLifecycleOwner)
        }
    }

    private fun toggleLikeButton(buttonType: String) {
        if (buttonType == getString(R.string.do_like)) {
            setButtonOff()
            showSnackbar(binding.root, "찜 목록에 추가되었습니다!", "확인하러 가기") {
                findNavController().navigate(R.id.likedListFragment)
            }
        } else {
            setButtonOn()
        }
    }

    private fun setButtonOn() {
        binding.textviewButtonLike.text = getString(R.string.do_like)
        binding.buttonLike.setBackgroundResource(R.drawable.frame_button_do_like)
        viewModel.setIsLiked(true)
    }

    private fun setButtonOff() {
        binding.textviewButtonLike.text = getString(R.string.cancle_like)
        binding.buttonLike.setBackgroundResource(R.drawable.frame_button_cancle_like)
        viewModel.setIsLiked(false)
    }

    private fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.concertDetail.collect { concertDetail ->
                Glide.with(binding.imageConcert)
                    .load(concertDetail.imageUrl)
                    .into(binding.imageConcert)
                binding.apply {
                    if (concertDetail.favorite) { setButtonOff() }
                    textviewTitle.text = concertDetail.name
                    textviewContent.text = concertDetail.info
                    textviewCity.text = getString(R.string.detail_item_locale, concertDetail.concertHallCity, concertDetail.concertHallName)
                    textviewDate.text = getString(R.string.detail_item_date, concertDetail.holdOpenDate, concertDetail.holdCloseDate)
//                    buttonLike.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onPause() {
        viewModel.postLike(binding.textviewButtonLike.text.toString(), TEST_USERSEQ)
        super.onPause()
    }

    companion object {
        private const val TEST_USERSEQ = 1
    }
}