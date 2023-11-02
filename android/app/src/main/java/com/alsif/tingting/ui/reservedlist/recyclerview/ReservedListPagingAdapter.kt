package com.alsif.tingting.ui.likedlist.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alsif.tingting.R
import com.alsif.tingting.data.model.TicketDto
import com.alsif.tingting.databinding.ItemReservedConcertBinding
import com.alsif.tingting.util.AnimUtil
import com.alsif.tingting.util.alphaAnimation
import com.alsif.tingting.util.revealAnimation
import com.alsif.tingting.util.scaleAnimation
import com.alsif.tingting.util.translateAnimation
import com.bumptech.glide.Glide

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
        @SuppressLint("ClickableViewAccessibility")
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
            binding.itemReservedConcert.setOnTouchListener { view, motionEvent ->
                when (motionEvent.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        view.scaleAnimation(AnimUtil.AnimDirection.XY, 0.94f, 100)
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        view.scaleAnimation(AnimUtil.AnimDirection.XY, 1f, 100)
                    }
                    MotionEvent.ACTION_UP -> {
                        itemClickListener.onClick(view, ticket)
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

        holder.binding.itemReservedConcert.animation = AnimationUtils.loadAnimation(holder.binding.itemReservedConcert.context, R.anim.list_item_anim_fade_in)
    }

    lateinit var itemClickListener: ItemClickListener
    interface ItemClickListener {
        fun onClick(view: View, ticketDto: TicketDto)
    }
}