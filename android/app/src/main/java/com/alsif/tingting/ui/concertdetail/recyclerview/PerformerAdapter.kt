package com.alsif.tingting.ui.concertdetail.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alsif.tingting.data.model.PerformerDto
import com.alsif.tingting.databinding.ItemPerformerBinding
import com.bumptech.glide.Glide

class PerformerAdapter : ListAdapter<PerformerDto, PerformerAdapter.PerformerViewHolder>(PerformerItemComperator) {
    companion object PerformerItemComperator : DiffUtil.ItemCallback<PerformerDto>() {
        override fun areItemsTheSame(oldItem: PerformerDto, newItem: PerformerDto): Boolean {
            return oldItem != newItem
        }

        override fun areContentsTheSame(oldItem: PerformerDto, newItem: PerformerDto): Boolean {
            return oldItem.seq != newItem.seq
        }

    }
    inner class PerformerViewHolder(val binding: ItemPerformerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(performerDto: PerformerDto) {
            Glide.with(binding.img)
                .load(performerDto.performerImageUrl)
                .into(binding.img)
            binding.textviewName.text = performerDto.performerName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformerViewHolder {
        return PerformerViewHolder(
            ItemPerformerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PerformerViewHolder, position: Int) {
        getItem(position)?.let { holder.bindInfo(it) }
    }
}