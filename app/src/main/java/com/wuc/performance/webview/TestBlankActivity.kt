package com.wuc.performance.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import com.wuc.performance.R
import com.wuc.performance.utils.LogUtils

class TestBlankActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_blank)
        val textView = findViewById<AppCompatTextView>(R.id.tv_testblank)
        textView.postDelayed({
            val isBlank = BlankDetect.isBlank(textView)
            LogUtils.i("isBlank $isBlank")
        },2000)
    }
}