package com.alsif.tingting.ui.search.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alsif.tingting.R
import com.alsif.tingting.databinding.FooterLoadingBinding

class PageLoadingAdapter : LoadStateAdapter<PageLoadingAdapter.PageLoadStateViewHolder>() {

    /**
     * LoadState 값을 받아 로딩 상태에 따라 ProgressBar의 visible 설정 처리
     */
    inner class PageLoadStateViewHolder(
        private val binding: FooterLoadingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            binding.lottieAnimationView.isVisible = loadState is LoadState.Loading
        }
    }

    override fun onBindViewHolder(holder: PageLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): PageLoadStateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.footer_loading, parent, false)
        val binding = FooterLoadingBinding.bind(view)

        return PageLoadStateViewHolder(binding)
    }
}