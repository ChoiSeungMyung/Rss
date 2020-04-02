package com.victoryzzi.android.apps.myrealtrip.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.victoryzzi.android.apps.myrealtrip.R
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val startIntent = Intent(this, NewsListActivity::class.java)

        splash_center_logo.apply {
            background = ShapeDrawable(OvalShape()).apply {
                paint.color = Color.LTGRAY
            }
            clipToOutline = true
        }

        CoroutineScope(Dispatchers.Main).launch {
            delay(1300)
            startActivity(startIntent)
            finish()
        }
    }
}
