package com.victoryzzi.android.apps.myrealtrip

import com.victoryzzi.android.apps.myrealtrip.extension.getKeyWord
import org.junit.Assert.assertTrue
import org.junit.Test

class StringTest {

    /**
     * 키워드 추출 함수 테스트
     *
     * case 1 : 한글로 입력된 문장
     * case 2 : 영어로 입력된 문장 && 단어 반복횟수 같음 + 예외로 걸러질 문자 포함
     * case 3 : 실제 기사
     */
    @Test
    fun keyWordExtractionSort() {
        val kr = "마이리얼트립, 최승명, 안드로이드, 마이리얼트립, 최승명, 마이리얼트립, 마이리얼트립, 최승명, 마이리얼트립, 안드로이드"
        val en = "for, for, for, for, for, for, trip, trip, trip, trip, test, test, test, test"
        val real = "삼성전자가 다음 달 공개할 예정인 차기 갤럭시 S시리즈 중 한 모델로 추정되는 사진(사진)이 유출됐다"

        val krExtraction = kr.getKeyWord()
        val enExtraction = en.getKeyWord()
        val realExtraction = real.getKeyWord()

        assertTrue(krExtraction[0].first == "마이리얼트립")
        assertTrue(krExtraction[1].first == "최승명")
        assertTrue(krExtraction[2].first == "안드로이드")


        assertTrue(enExtraction.size == 2)
        assertTrue(enExtraction[0].first == "test")
        assertTrue(enExtraction[1].first == "trip")

        assertTrue(realExtraction[0].first == "s시리즈")
        assertTrue(realExtraction[1].first == "갤럭시")
        assertTrue(realExtraction[2].first == "공개할")
    }
}