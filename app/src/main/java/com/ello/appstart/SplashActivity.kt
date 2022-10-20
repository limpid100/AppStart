package com.ello.appstart

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ello.appstart.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.timerTask
import kotlin.math.log
import kotlin.math.roundToInt

/**
 * @author dxl
 * @date 2022-10-19  周三
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {


    private var resourceReady = false

    private lateinit var vb: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        immerseStatus()

        installSplashScreen().setKeepOnScreenCondition {
            !resourceReady
        }
        vb = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(vb.root)

        lifecycleScope.launch {
            //模拟接口获取启动图
            delay(500)
            //获取到启动图
            val url =
                "https://c-ssl.dtstatic.com/uploads/blog/202106/11/20210611080116_6db1d.thumb.1000_0.jpeg"
            Glide.with(this@SplashActivity).load(url)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        goMain()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        resourceReady = true
                        startCountDown()
                        return false
                    }

                }).into(vb.ivSplash)
        }

    }


    private var countDownTimer:CountDownTimer? = null

    private fun startCountDown() {
        countDownTimer?.cancel()
        Log.d(TAG, "startCountDown: ")
        countDownTimer = object : CountDownTimer(3000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d(TAG, "onTick: $millisUntilFinished")
                if (vb.btnSkip.isVisible.not()) {
                    vb.btnSkip.isVisible = true
                }

                vb.btnSkip.text = "跳过 ${(millisUntilFinished / 1000.0).roundToInt()}s"
            }

            override fun onFinish() {
                goMain()
            }

        }
        countDownTimer?.start()
        vb.btnSkip.setOnClickListener {
            countDownTimer?.cancel()
            goMain()
        }
    }

    private fun goMain() {
        finish()
        overridePendingTransition(0, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }

}