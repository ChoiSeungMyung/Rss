package com.victoryzzi.android.apps.myrealtrip.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.victoryzzi.android.apps.myrealtrip.extension.getKeyWord
import com.victoryzzi.android.apps.myrealtrip.model.News
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.net.SocketTimeoutException
import javax.net.ssl.SSLException

class NewsListViewModel(application: Application) : AndroidViewModel(application) {
    private val newsList = ArrayList<News>()
    val allNews: MutableLiveData<List<News>> = MutableLiveData()

    init {
        newsList.clear()
    }

    fun initNewsList() = newsList.clear()

    /**
     * rss에서 <item> 요소들만 추출 -> 상세페이지로 넘어가기 위해 다른 코루틴 실행
     */
    fun getResult() {
        CoroutineScope(Dispatchers.IO).launch {
            val rss = Jsoup.connect("https://news.google.com/rss").get()
            val rssElements = rss.select("item")

            rssElements.forEach { element ->
                getContents(element)
            }
        }
    }

    /**
     * 상세페이지 링크로 접속해 썸네일, 본문, 제목을 가져옴
     */
    private fun getContents(element: Element) {
        var title = ""
        var imageUrl = ""
        var description = ""
        val newsUrl = element.select("link").text()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val newsHTML = Jsoup.connect(newsUrl).get()
                title =
                    newsHTML.select("meta[property=og:title]").first()
                        .attr("content")
                imageUrl =
                    newsHTML.select("meta[property=og:image]").first()
                        .attr("content")
                description =
                    newsHTML.select("meta[property=og:description]").first()
                        .attr("content")
            } catch (e: NullPointerException) {
                e.printStackTrace()
            } catch (e: HttpStatusException) {
                e.printStackTrace()
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
            } catch (e: SSLException) {
                e.printStackTrace()
            } finally {
                if (title.isNotEmpty() && imageUrl.isNotEmpty() && description.isNotEmpty()) {
                    newsList.add(
                        News(
                            title,
                            imageUrl,
                            description,
                            newsUrl,
                            description.getKeyWord()
                        )
                    )
                    withContext(Dispatchers.Main) {
                        allNews.value = newsList
                    }
                }
            }
        }
    }
}