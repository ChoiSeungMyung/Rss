package com.victoryzzi.android.apps.myrealtrip.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.victoryzzi.android.apps.myrealtrip.R
import com.victoryzzi.android.apps.myrealtrip.databinding.ActivityNewsDetailBinding
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetailActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail) as ActivityNewsDetailBinding

        binding.apply {
            newsTitle = intent.getStringExtra("newsTitle")
            newsKeyword0 = intent.getStringExtra("newsKeyword0")
            newsKeyword1 = intent.getStringExtra("newsKeyword1")
            newsKeyword2 = intent.getStringExtra("newsKeyword2")
        }

        val newsUrl = intent.getStringExtra("url")
        newsUrl?.let {
            news_detail_web.apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                settings.javaScriptCanOpenWindowsAutomatically = false
                settings.setSupportMultipleWindows(false)
                settings.useWideViewPort = true
                loadUrl(it)
            }
        }
    }
}