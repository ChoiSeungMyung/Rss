package com.victoryzzi.android.apps.myrealtrip.model

data class News(
    val title: String,
    val thumb: String,
    val description: String,
    val originalUrl: String,
    val keyWord: List<Pair<String, Int>>
)