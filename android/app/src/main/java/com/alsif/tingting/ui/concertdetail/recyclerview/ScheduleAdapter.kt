package com.alsif.tingting.ui.concertdetail.recyclerview

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alsif.tingting.R
import com.alsif.tingting.data.model.ConcertDto
import com.alsif.tingting.data.model.ConcertScheduleDto
import com.alsif.tingting.databinding.ItemScheduleBinding
import com.alsif.tingting.util.AnimUtil
import com.alsif.tingting.util.extension.nowTime
import com.alsif.tingting.util.extension.parseLong
import com.alsif.tingting.util.extension.toLocalDateTime
import com.alsif.tingting.util.scaleAnimation
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ScheduleAdapter : ListAdapter<ConcertScheduleDto, ScheduleAdapter.ScheduleItemViewHolder>(ScheduleItemComperator) {
    companion object ScheduleItemComperator: DiffUtil.ItemCallback<ConcertScheduleDto>() {
        override fun areItemsTheSame(
            oldItem: ConcertScheduleDto,
            newItem: ConcertScheduleDto
        ): Boolean {
            return oldItem != newItem
        }

        override fun areContentsTheSame(
            oldItem: ConcertScheduleDto,
            newItem: ConcertScheduleDto
        ): Boolean {
            return oldItem.seq != newItem.seq
        }

    }

    inner class ScheduleItemViewHolder(val binding: ItemScheduleBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ClickableViewAccessibility")
        fun bindInfo(scheduleDto: ConcertScheduleDto) {
            binding.textviewDate.text = scheduleDto.holdDate.substring(0, 11)
            binding.textviewTime.text = scheduleDto.holdDate.subSequence(12, 16)
            if (scheduleDto.holdDate.toLocalDateTime().parseLong() <= nowTime()) {
                binding.buttonReservation.setBackgroundResource(R.drawable.frame_button_reservation_none)
                binding.buttonReservation.isClickable = false
                binding.buttonReservation.isEnabled = false
            }
            binding.buttonReservation.setOnTouchListener { view, motionEvent ->
                when (motionEvent.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        view.scaleAnimation(AnimUtil.AnimDirection.XY, 0.94f, 100)
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        view.scaleAnimation(AnimUtil.AnimDirection.XY, 1f, 100)
                    }
                    MotionEvent.ACTION_UP -> {
                        buttonClickListener.onClick(view, scheduleDto)
                        view.scaleAnimation(AnimUtil.AnimDirection.XY, 1f, 100)
                    }
                }
                true
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScheduleAdapter.ScheduleItemViewHolder {
        return ScheduleItemViewHolder(
            ItemScheduleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ScheduleAdapter.ScheduleItemViewHolder, position: Int) {
        holder.bindInfo(getItem(position))
    }

    lateinit var buttonClickListener: ButtonClickListener
    interface ButtonClickListener {
        fun onClick(view: View, scheduleDto: ConcertScheduleDto)
    }
}