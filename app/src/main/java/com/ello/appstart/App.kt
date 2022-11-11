package com.ello.appstart

import android.app.Application
import android.util.Log
import com.blankj.utilcode.util.SPUtils

/**
 * @author dxl
 * @date 2022-10-19  周三
 */

const val TAG = "AppStart"

class App : Application() {

    companion object {
        var privacyConfirmed = false
            set(value) {
                field = value
                SPUtils.getInstance().put("privacy_agreed", value, true)
            }
            get() = SPUtils.getInstance().getBoolean("privacy_agreed")
    }


    override fun onCreate() {
        super.onCreate()
        if (privacyConfirmed) {
            //隐私协议同意了，进行第三方SDK初始化
            initWithPrivacyAgreed()
        }
    }

    /**
     * 隐私协议同意后执行的逻辑，比如初始化第三方sdk
     *
     */
    private fun initWithPrivacyAgreed() {
        Log.d(TAG, "initWithPrivacyAgreed: ")
    }
}