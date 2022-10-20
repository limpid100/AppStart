package com.ello.appstart

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        if (!App.privacyConfirmed) {
            //隐私协议没同意，直接跳转隐私协议页面，当前页面结束，不进行初始化了
            startActivityWithNoAnim<PrivacyActivity>()
            finish()
            return
        }

        //隐私协议同意了，跳转闪屏页面，覆盖住当前页面，让当前页面静默加载数据
        startActivityWithNoAnim<SplashActivity>()

        setContentView(R.layout.activity_main)
        initData()
    }

    private fun initData() {
        //加载数据，更新UI等
        Log.d(TAG, "MainActivity: initData")

        lifecycleScope.launch {
            delay(1000)
            findViewById<TextView>(R.id.tvData).text = "数据加载成功"
        }

    }

}