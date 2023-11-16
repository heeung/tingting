package com.alsif.tingting.ui.reserve.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alsif.tingting.R
import com.alsif.tingting.data.model.SeatSelectionDto
import com.alsif.tingting.databinding.ItemSelectedSeatBinding

private const val TAG = "SelectSeatListAdapter"
class SelectSeatListAdapter(val list: List<SeatSelectionDto>) : RecyclerView.Adapter<SelectSeatListAdapter.SelectSeatViewHolder>() {
    inner class SelectSeatViewHolder(val binding: ItemSelectedSeatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(seatSelectionDto: SeatSelectionDto) {
            if (seatSelectionDto.grade == "VIP") {
                binding.frameGrade.setBackgroundResource(R.drawable.frame_section_vip)
            } else {
                binding.frameGrade.setBackgroundResource(R.drawable.frame_section_common)
            }
            binding.textviewSection.text = seatSelectionDto.section
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

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SelectSeatViewHolder, position: Int) {
        holder.bindInfo(list[position])
    }
}