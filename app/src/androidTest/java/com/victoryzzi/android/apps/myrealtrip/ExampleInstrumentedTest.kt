package com.victoryzzi.android.apps.myrealtrip

import android.content.Context
import android.graphics.Point
import android.util.Size
import android.view.View
import android.view.WindowManager
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.victoryzzi.android.apps.myrealtrip", appContext.packageName)
    }
}

/**
 * 해당 뷰가 화면 중앙 정렬인지 확인
 */
fun isCenterHorizontally(): Matcher<View> =
    object : TypeSafeMatcher<View>() {
        override fun matchesSafely(item: View?): Boolean {
            val windowManager =
                item?.context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val displaySize = Point()
            windowManager.defaultDisplay.apply {
                getSize(displaySize)
            }
            val width = displaySize.x + 1

            val outLocation = IntArray(2)
            item.getLocationOnScreen(outLocation)

            val viewLeft = outLocation[0]
            val rightMargin = width - (viewLeft + item.measuredWidth)

            return Math.abs(rightMargin - viewLeft) < 2
        }

        override fun describeTo(description: Description?) {
        }
    }