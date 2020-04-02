package com.victoryzzi.android.apps.myrealtrip.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.victoryzzi.android.apps.myrealtrip.R
import com.victoryzzi.android.apps.myrealtrip.adapter.NewsListAdapter
import com.victoryzzi.android.apps.myrealtrip.adapter.viewholder.NewsItemClick
import com.victoryzzi.android.apps.myrealtrip.databinding.ActivityNewsListBinding
import com.victoryzzi.android.apps.myrealtrip.extension.makeSnackBar
import com.victoryzzi.android.apps.myrealtrip.extension.networkCheck
import com.victoryzzi.android.apps.myrealtrip.model.News
import com.victoryzzi.android.apps.myrealtrip.viewmodel.NewsListViewModel
import kotlinx.android.synthetic.main.activity_news_list.*
import kotlinx.coroutines.*

class NewsListActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var newsListViewModel: NewsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsListViewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)

        val binding: ActivityNewsListBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_news_list)
        binding.newsListViewModel = newsListViewModel

        val newsListAdapter = NewsListAdapter(newsClickListener)
        news_list_view.adapter = newsListAdapter

        newsListViewModel.allNews.observe(this, Observer { newsList ->
            newsListAdapter.submitList(newsList)
        })

        // 네트워크 상태 확인 후 연결이 되어있으면 목록 불러오기 && 안되어 있으면 연결상태 확인 스낵바 노출
        when (networkCheck()) {
            true -> {
                newsListViewModel.getResult()
            }
            false -> news_list_root.makeSnackBar(resources.getString(R.string.toast_check_connectivity))
        }

        news_list_refresh.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        when (networkCheck()) {
            true -> {
                newsListViewModel.run {
                    initNewsList()
                    getResult()
                }
                CoroutineScope(Dispatchers.Default).launch {
                    delay(2000)
                    withContext(Dispatchers.Main) {
                        news_list_refresh.isRefreshing = false
                        news_list_view.adapter?.notifyDataSetChanged()
                    }
                }
            }

            false -> news_list_root.makeSnackBar(resources.getString(R.string.toast_check_connectivity))
        }
    }

    private val newsClickListener = object : NewsItemClick {
        override fun onClick(item: News) {
            val intent = Intent(this@NewsListActivity, NewsDetailActivity::class.java).apply {
                putExtra("url", item.originalUrl)
                putExtra("newsTitle", item.title)
                putExtra("newsKeyword0", item.keyWord[0].first)
                putExtra("newsKeyword1", item.keyWord[1].first)
                putExtra("newsKeyword2", item.keyWord[2].first)
            }

            startActivity(intent)
        }
    }
}