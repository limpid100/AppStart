package com.ello.appstart

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * @author dxl
 * @date 2022-10-20  周四
 */

/**
 * 沉浸状态栏
 */
fun Activity.immerseStatus() {
    window.statusBarColor = Color.TRANSPARENT
    WindowCompat.setDecorFitsSystemWindows(window, false)
}

/**
 * 全屏，状态栏也没有
 */
fun Activity.fullScreen() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowCompat.getInsetsController(window, window.decorView)
        .apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
}

/**
 * 系统浏览器打开链接
 *
 * @param url
 */
fun Context.openWebWithSystemBrowser(url: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}

/**
 * 重新打开app
 */
fun Activity.restartApp() {
    val componentName = packageManager.getLaunchIntentForPackage(packageName)?.component?:return
    val mainIntent = Intent.makeRestartActivityTask(componentName)
    startActivity(mainIntent)
    Runtime.getRuntime().exit(0)
}

inline fun <reified T>Activity.startActivityWithNoAnim() {
    startActivity(Intent(this, T::class.java))
    overridePendingTransition(0, 0)
}