package com.victoryzzi.android.apps.myrealtrip.extension

/**
 * 조사, 어미 인지 판단
 */
private val enPostpositions =
    listOf(
        "to",
        "has",
        "the",
        "are",
        "is",
        "and",
        "of",
        "that",
        "with",
        "for",
        "on",
        "at",
        "or",
        "as",
        "its",
        "was",
        "were",
        "in",
        "be",
        "it",
        "from",
        "had",
        "this",
        "from",
        "thats",
        "who",
        "he",
        "but",
        "his",
        "could",
        "can",
        "an",
        "been",
        "have",
        "about",
        "when",
        "what",
        "where",
        "also"
    )

/**
 * 키워드 추출을 위한 확장 함수
 *
 * 단어별로 Pair를 생성해 단어, 횟수로 저장
 * 해당 단어가 first인 Pair객체가 있다면 (second + 1)로 업데이트
 *
 * Comparator를 통해 횟수가 많은 순서 > 가나다라순으로 정렬
 */
fun String?.getKeyWord(): List<Pair<String, Int>> {
    val keywords = HashMap<String, Int>()

    val compare = object : Comparator<Pair<String, Int>> {
        override fun compare(p0: Pair<String, Int>?, p1: Pair<String, Int>?): Int {
            if (p0 != null && p1 != null) {
                val count = p0.second - p1.second
                return when {
                    count > 0 -> -1
                    count < 0 -> 1
                    p0.first > p1.first -> 1
                    else -> -1
                }
            }
            return -1
        }
    }

    this?.let {
        it.regex().split(" ").forEach { keyword ->
            if (keyword.length > 1) {
                val keywordLower = keyword.toLowerCase()
                when (keywords.containsKey(keywordLower)) {
                    true -> {
                        val num = keywords[keyword] ?: 1
                        keywords[keyword] = num + 1
                    }
                    false -> {
                        if (!enPostpositions.contains(keywordLower)) {
                            keywords[keywordLower] = 1
                        }
                    }
                }
            }
        }
    }

    return keywords.toList().sortedWith(compare).take(3)
}

/**
 * String 특수문자 제거 (ex: ".", ",", """, ...)
 */
fun String.regex(): String {
    val match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]".toRegex()
    return this.replace(match, "")
}