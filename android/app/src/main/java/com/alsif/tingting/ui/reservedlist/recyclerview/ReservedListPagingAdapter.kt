package com.alsif.tingting.ui.likedlist.recyclerview

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alsif.tingting.R
import com.alsif.tingting.data.model.TicketDto
import com.alsif.tingting.databinding.ItemReservedConcertBinding
import com.alsif.tingting.util.AnimUtil
import com.alsif.tingting.util.alphaAnimation
import com.alsif.tingting.util.collapse
import com.alsif.tingting.util.expand
import com.alsif.tingting.util.flipAnimatinon
import com.alsif.tingting.util.revealAnimation
import com.alsif.tingting.util.scaleAnimation
import com.alsif.tingting.util.translateAnimation
import com.bumptech.glide.Glide

private const val TAG = "ReservedListPagingAdapt"
class ReservedListPagingAdapter : PagingDataAdapter<TicketDto, ReservedListPagingAdapter.ReservedListHolder>(ReservedListComparator) {
    companion object ReservedListComparator : DiffUtil.ItemCallback<TicketDto>() {
        override fun areItemsTheSame(oldItem: TicketDto, newItem: TicketDto): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TicketDto, newItem: TicketDto): Boolean {
            return oldItem.ticketSeq  == newItem.ticketSeq
        }
    }

    inner class ReservedListHolder(val binding: ItemReservedConcertBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindInfo(ticket : TicketDto) {
            Glide.with(binding.img)
                .load(ticket.imageUrl)
//                .placeholder(R.drawable.image_loading)
                .into(binding.img)
            binding.textviewTitle.text = ticket.name
            binding.textviewCity.text = "${ticket.concertHallCity} / ${ticket.concertHallName}"
            binding.textviewDate.text = ticket.holdDate.subSequence(0, 10).toString()
            binding.textviewTime.text = ticket.holdDate.subSequence(11, 16).toString()
            binding.textviewSeatCount.text = "${ticket.seats.size} 석"
            binding.textviewSection.text = ticket.sectionInfo()
            binding.textviewSeatInformation.text = "${ticket.seatsInfo()}"
            binding.textviewReservationDate.text = ticket.createdDate.subSequence(0, 16).toString()
            binding.textviewTotalPrice.text = "${ticket.totalPrice} 원"
            if (ticket.isExpanded) {
                binding.layoutExpand.visibility = View.VISIBLE
            } else {
                binding.layoutExpand.visibility = View.GONE
            }
            if (ticket.deletedDate == null) { // 취소표 아닌 경우
                binding.buttonReservationCancel.visibility = View.VISIBLE
                binding.layoutCancelDate.visibility = View.GONE
                binding.layoutReservedInfo.visibility = View.INVISIBLE
            } else { // 취소표인 경우
                binding.buttonReservationCancel.visibility = View.GONE
                binding.textviewCancelDate.text = ticket.deletedDate.subSequence(0, 16).toString()
                binding.layoutCancelDate.visibility = View.VISIBLE
                binding.layoutReservedInfo.visibility = View.VISIBLE
            }
            binding.buttonArrow.flipAnimatinon(ticket.isExpanded)

            setClickListeners(ticket)
        }
        private fun toggleLayout(isExpanded: Boolean): Boolean {
            binding.buttonArrow.flipAnimatinon(isExpanded)
            if (isExpanded) {
                binding.layoutExpand.expand()
            } else {
                binding.layoutExpand.collapse()
            }
            return isExpanded
        }

        @SuppressLint("ClickableViewAccessibility")
        private fun setClickListeners(ticket : TicketDto) {
            binding.itemReservedConcert.setOnTouchListener { view, motionEvent ->
                when (motionEvent.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        view.scaleAnimation(AnimUtil.AnimDirection.XY, 0.94f, 100)
//                        binding.layoutConcertItem.scaleAnimation(AnimUtil.AnimDirection.XY, 0.94f, 100)
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        view.scaleAnimation(AnimUtil.AnimDirection.XY, 1f, 100)
//                        binding.layoutConcertItem.scaleAnimation(AnimUtil.AnimDirection.XY, 1f, 100)
                    }
                    MotionEvent.ACTION_UP -> {
                        val show = toggleLayout(!ticket.isExpanded)
                        ticket.isExpanded = show
                        view.scaleAnimation(AnimUtil.AnimDirection.XY, 1f, 100)
//                        binding.layoutConcertItem.scaleAnimation(AnimUtil.AnimDirection.XY, 1f, 100)
                    }
                }
                true
            }
            binding.buttonConcertDetail.setOnTouchListener { view, motionEvent ->
                when (motionEvent.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        view.scaleAnimation(AnimUtil.AnimDirection.XY, 0.94f, 100)
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        view.scaleAnimation(AnimUtil.AnimDirection.XY, 1f, 100)
                    }
                    MotionEvent.ACTION_UP -> {
                        concertDatailClickListener.onClick(view, ticket)
                        view.scaleAnimation(AnimUtil.AnimDirection.XY, 1f, 100)
                    }
                }
                true
            }
            binding.buttonReservationCancel.setOnTouchListener { view, motionEvent ->
                when (motionEvent.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        view.scaleAnimation(AnimUtil.AnimDirection.XY, 0.94f, 100)
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        view.scaleAnimation(AnimUtil.AnimDirection.XY, 1f, 100)
                    }
                    MotionEvent.ACTION_UP -> {
                        reservationCancelClickListener.onClick(view, ticket)
                        view.scaleAnimation(AnimUtil.AnimDirection.XY, 1f, 100)
                    }
                }
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservedListHolder {
        return ReservedListHolder(
            ItemReservedConcertBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReservedListHolder, position: Int) {
        getItem(position)?.let { holder.bindInfo(it) }

        holder.binding.layoutConcertItem.animation = AnimationUtils.loadAnimation(holder.binding.layoutConcertItem.context, R.anim.list_item_anim_fade_in)
    }

    lateinit var itemClickListener: ItemClickListener
    interface ItemClickListener {
        fun onClick(view: View, ticketDto: TicketDto)
    }
    lateinit var reservationCancelClickListener: ReservationCancelClickListener
    interface ReservationCancelClickListener {
        fun onClick(view: View, ticketDto: TicketDto)
    }
    lateinit var concertDatailClickListener: ConcertDatailClickListener
    interface ConcertDatailClickListener {
        fun onClick(view: View, ticketDto: TicketDto)
    }
}