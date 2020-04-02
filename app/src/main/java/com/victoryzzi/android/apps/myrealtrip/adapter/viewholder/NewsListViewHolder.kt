package com.victoryzzi.android.apps.myrealtrip.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victoryzzi.android.apps.myrealtrip.databinding.ListNewsItemBinding
import com.victoryzzi.android.apps.myrealtrip.model.News

class NewsListViewHolder(private val binding: ListNewsItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(listener: NewsItemClick, item: News) = binding.apply {
        news = item

        Glide
            .with(binding.root)
            .load(item.thumb)
            .centerCrop()
            .into(itemNewsThumb)

        clickListener = View.OnClickListener {
            listener.onClick(item)
        }
    }
}

interface NewsItemClick {
    fun onClick(item: News)
}