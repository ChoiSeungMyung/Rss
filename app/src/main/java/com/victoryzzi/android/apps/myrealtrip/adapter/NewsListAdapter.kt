package com.victoryzzi.android.apps.myrealtrip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.victoryzzi.android.apps.myrealtrip.R
import com.victoryzzi.android.apps.myrealtrip.adapter.viewholder.NewsItemClick
import com.victoryzzi.android.apps.myrealtrip.adapter.viewholder.NewsListViewHolder
import com.victoryzzi.android.apps.myrealtrip.model.News

class NewsListAdapter(private val listener: NewsItemClick) :
    ListAdapter<News, NewsListViewHolder>(NewsDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder =
        NewsListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_news_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        holder.bind(listener, getItem(position))
    }
}

class NewsDiffCallBack : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean = oldItem == newItem
}