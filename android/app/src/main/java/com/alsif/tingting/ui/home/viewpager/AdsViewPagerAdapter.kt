package com.alsif.tingting.ui.home.viewpager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alsif.tingting.R
import com.alsif.tingting.data.model.PagerDataDto
import com.alsif.tingting.databinding.ItemAdsBinding
import com.bumptech.glide.Glide

class AdsViewPagerAdapter(private val listData: ArrayList<PagerDataDto>) : RecyclerView.Adapter<AdsViewPagerAdapter.AdsViewPagerHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewPagerHolder {
        return AdsViewPagerHolder(
            ItemAdsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: AdsViewPagerHolder, position: Int) {
        val viewHolder: AdsViewPagerHolder = holder
        viewHolder.bindInfo(listData[position % listData.size])
    }

    inner class AdsViewPagerHolder(val binding: ItemAdsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(data: PagerDataDto) {
            Glide.with(binding.img)
                .load(data.imgUrl)
                .into(binding.img)
        }
    }

    override fun getItemCount(): Int = Int.MAX_VALUE
}