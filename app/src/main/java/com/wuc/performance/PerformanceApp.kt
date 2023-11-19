package com.wuc.performance

import android.app.Application

/**
 * @author     wuchao
 * @date       2023/11/19 15:38
 * @description
 */
class PerformanceApp: Application() {

    companion object {
       lateinit var mApplication: Application
        @JvmStatic
        fun getApplication(): Application {
            return mApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        mApplication = this
    }
}