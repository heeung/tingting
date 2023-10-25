package com.alsif.tingting.ui.concertdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.databinding.FragmentConcertDetailBinding
import com.alsif.tingting.ui.home.HomeFragmentViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConcertDetailFragment: BaseFragment<FragmentConcertDetailBinding>(FragmentConcertDetailBinding::bind, R.layout.fragment_concert_detail) {

    private val viewModel: ConcertDetailFragmentViewModel by viewModels()
    val args: ConcertDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe()
        getConcertDetail()
    }

    private fun getConcertDetail() {
        viewModel.getConcertDetail(args.concertSeq, TEST_USERSEQ)
    }

    private fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.concertDetail.collect { concertDetail ->
                Glide.with(binding.imageConcert)
                    .load(concertDetail.imageUrl)
                    .into(binding.imageConcert)
                binding.apply {
                    textviewTitle.text = concertDetail.name
                    textviewContent.text = concertDetail.info
                    textviewCity.text = "장소 : " + concertDetail.concertHallCity + " / " + concertDetail.concertHallName
                    textviewDate.text = "일시 : " + concertDetail.holdOpenDate + " ~ " + concertDetail.holdCloseDate
                    textviewPerformer.text = concertDetail.performers[0].performerName
                }
            }
        }
    }

    companion object {
        private const val TEST_USERSEQ = 1
    }
}