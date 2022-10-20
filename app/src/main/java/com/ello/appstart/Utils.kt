package com.ello.appstart

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.view.WindowInsets
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

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
    WindowCompat.getInsetsController(window, window.decorView)
        .hide(WindowInsetsCompat.Type.statusBars())
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