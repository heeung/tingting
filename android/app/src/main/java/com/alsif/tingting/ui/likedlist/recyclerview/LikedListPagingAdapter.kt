package com.alsif.tingting.ui.likedlist.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.paging.PagingState
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alsif.tingting.R
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.databinding.ItemConcertBinding
import com.alsif.tingting.databinding.ItemLikedConcertBinding
import com.bumptech.glide.Glide

class LikedListPagingAdapter : PagingDataAdapter<ConcertDto, LikedListPagingAdapter.LikedConcertListHolder>(ConcertListComparator) {
    companion object ConcertListComparator : DiffUtil.ItemCallback<ConcertDto>() {
        override fun areItemsTheSame(oldItem: ConcertDto, newItem: ConcertDto): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ConcertDto, newItem: ConcertDto): Boolean {
            return oldItem.concertSeq  == newItem.concertSeq
        }
    }

    inner class LikedConcertListHolder(val binding: ItemLikedConcertBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindInfo(concert : ConcertDto) {
            Glide.with(binding.img)
                .load(concert.imageUrl)
//                .placeholder(R.drawable.image_loading)
                .into(binding.img)
            binding.textviewTitle.text = concert.name
            binding.textviewCity.text = concert.concertHallCity
            binding.textviewDate.text = "${concert.holdOpenDate.subSequence(0, 10)} ~ ${concert.holdCloseDate.subSequence(0, 10)}"
            binding.itemLikedConcert.setOnClickListener {
                itemClickListner.onClick(it, concert)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedConcertListHolder {
        return LikedConcertListHolder(
            ItemLikedConcertBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LikedConcertListHolder, position: Int) {
        getItem(position)?.let { holder.bindInfo(it) }

        holder.binding.itemLikedConcert.animation = AnimationUtils.loadAnimation(holder.binding.itemLikedConcert.context, R.anim.list_item_anim_fade_in)
    }

    lateinit var itemClickListner: ItemClickListener
    interface ItemClickListener {
        fun onClick(view: View, concertDto: ConcertDto)
    }
}