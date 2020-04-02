package com.victoryzzi.android.apps.myrealtrip.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.victoryzzi.android.apps.myrealtrip.R
import com.victoryzzi.android.apps.myrealtrip.isCenterHorizontally
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SplashActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(SplashActivity::class.java, false, false)

    /**
     * SplashActivity에서 appVersion이 value/string.xml과 동일한지 확인
     */
    @Test
    fun appVersionEqualsStringValue() {
        ActivityScenario.launch(SplashActivity::class.java)
        Espresso.onView(withId(R.id.splash_app_version))
            .check(matches(withText(R.string.splash_app_version)))
    }

    /**
     * SplashActivity에서 앱 설명이 중앙정렬 되어있는지 확인
     */
    @Test
    fun isHorizontally() {
        ActivityScenario.launch(SplashActivity::class.java)
        Espresso.onView(withId(R.id.splash_app_description_first))
            .check(matches(isCenterHorizontally()))
    }
}