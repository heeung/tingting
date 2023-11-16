package com.alsif.tingting.ui.reserve.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alsif.tingting.R
import com.alsif.tingting.data.model.PerformerDto
import com.alsif.tingting.data.model.SeatSelectionDto
import com.alsif.tingting.databinding.ItemPerformerBinding
import com.alsif.tingting.databinding.ItemSelectedSeatBinding
import com.bumptech.glide.Glide

private const val TAG = "SelectSeatAdapter"
class SelectSeatAdapter : ListAdapter<SeatSelectionDto, SelectSeatAdapter.SelectSeatViewHolder>(ItemComparator) {
    companion object ItemComparator : DiffUtil.ItemCallback<SeatSelectionDto>() {
        override fun areItemsTheSame(oldItem: SeatSelectionDto, newItem: SeatSelectionDto): Boolean {
            return oldItem.concertSeatInfoSeq != newItem.concertSeatInfoSeq
        }

        override fun areContentsTheSame(oldItem: SeatSelectionDto, newItem: SeatSelectionDto): Boolean {
            return oldItem.seat != newItem.seat
        }

    }
    inner class SelectSeatViewHolder(val binding: ItemSelectedSeatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(seatSelectionDto: SeatSelectionDto) {
            if (seatSelectionDto.grade == "VIP") {
                binding.frameGrade.setBackgroundResource(R.drawable.frame_section_vip)
            } else {
                binding.frameGrade.setBackgroundResource(R.drawable.frame_section_common)
            }
            binding.textviewSeatNum.text = seatSelectionDto.seat
            binding.textviewCost.text = seatSelectionDto.price.toString() + " 원"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectSeatViewHolder {
        return SelectSeatViewHolder(
            ItemSelectedSeatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SelectSeatViewHolder, position: Int) {
        getItem(position)?.let { holder.bindInfo(it) }
        Log.d(TAG, "onBindViewHolder: ${position}???????????${getItem(position)}")
    }
}