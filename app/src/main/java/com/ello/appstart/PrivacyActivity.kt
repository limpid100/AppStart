package com.ello.appstart

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.blankj.utilcode.util.AppUtils


/**
 * @author dxl
 * @date 2022-10-20  周四
 */
class PrivacyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        //背景色设置为主题色
        window.decorView.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
        //沉浸状态栏
        immerseStatus()
        //显示隐私协议提示框
        showPrivacyDialog()
    }

    private fun showPrivacyDialog() {
        val message = SpannableString("请查看《隐私协议》，并同意，否则将退出应用").apply {
            setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    openWebWithSystemBrowser("https://www.baidu.com/s?wd=App%E9%9A%90%E7%A7%81%E5%8D%8F%E8%AE%AE%E6%80%8E%E4%B9%88%E5%86%99")
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.color = Color.BLUE
                    ds.isUnderlineText = true
                }

            }, 3, 9, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("隐私协议")
            .setMessage(message)
            .setPositiveButton("同意") { _, _ ->
                //同意后，重启app
                App.privacyConfirmed = true
                restartApp()
            }
            .setNegativeButton(
                "退出应用"
            ) { _, _ ->
                AppUtils.exitApp()
            }
            .show()

        //让链接可以被点击
        alertDialog.findViewById<TextView>(android.R.id.message)?.let {
            it.movementMethod = LinkMovementMethod.getInstance()
            it.highlightColor = Color.TRANSPARENT
        }
    }



}