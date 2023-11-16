package com.alsif.tingting.ui.concertdetail

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.annotation.DrawableRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseFragment
import com.alsif.tingting.data.model.ConcertScheduleDto
import com.alsif.tingting.databinding.FragmentConcertDetailBinding
import com.alsif.tingting.ui.concertdetail.recyclerview.PerformerAdapter
import com.alsif.tingting.ui.concertdetail.recyclerview.ScheduleAdapter
import com.alsif.tingting.ui.home.HomeFragmentDirections
import com.alsif.tingting.ui.home.HomeFragmentViewModel
import com.alsif.tingting.ui.search.SearchFragment
import com.alsif.tingting.util.AnimUtil
import com.alsif.tingting.util.clickAnimation
import com.alsif.tingting.util.extension.setStatusBarTransparent
import com.alsif.tingting.util.scaleAnimation
import com.alsif.tingting.util.setScaleXY
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConcertDetailFragment: BaseFragment<FragmentConcertDetailBinding>(FragmentConcertDetailBinding::bind, R.layout.fragment_concert_detail) {

    private val viewModel: ConcertDetailFragmentViewModel by viewModels()
    private val args: ConcertDetailFragmentArgs by navArgs()
    private lateinit var performerListAdapter: PerformerAdapter
    private lateinit var scheduleListAdapter: ScheduleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        subscribe()
        setClickListeners()
        getConcertDetail()
    }

    private fun getConcertDetail() {
        viewModel.getConcertDetail(args.concertSeq, TEST_USERSEQ)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setClickListeners() {
        binding.buttonLike.setOnTouchListener { view, motionEvent ->
            when (motionEvent.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    view.scaleAnimation(AnimUtil.AnimDirection.XY, 0.9f, 100)
                }
                MotionEvent.ACTION_CANCEL -> {
                    view.scaleAnimation(AnimUtil.AnimDirection.XY, 1f, 100)
                }
                MotionEvent.ACTION_UP -> {
                    toggleLikeButton(binding.textviewButtonLike.text.toString())
                    view.scaleAnimation(AnimUtil.AnimDirection.XY, 1f, 100)
                }
            }
            true
        }
        scheduleListAdapter.buttonClickListener = object : ScheduleAdapter.ButtonClickListener {
            override fun onClick(view: View, scheduleDto: ConcertScheduleDto) {
                val action = ConcertDetailFragmentDirections.actionConcertDetailFragmentToReserveFragment(scheduleDto.seq, viewModel.concertHallInfo.value.concertHallSeq)
                findNavController().navigate(action)
            }
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
    }

    private fun setButtonOff() {
        binding.textviewButtonLike.text = getString(R.string.cancle_like)
        binding.buttonLike.setBackgroundResource(R.drawable.frame_button_cancle_like)
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
                performerListAdapter.submitList(concertDetail.performers)
                scheduleListAdapter.submitList(concertDetail.concertDetails)
            }
        }
    }

    private fun initRecyclerView() {
        performerListAdapter = PerformerAdapter()
        binding.recyclerPerformer.apply {
            adapter = performerListAdapter
            layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false)
        }
        scheduleListAdapter = ScheduleAdapter()
        binding.recyclerConcertSchedule.apply {
            adapter = scheduleListAdapter
            layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
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