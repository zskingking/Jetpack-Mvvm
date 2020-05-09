package com.zs.base_library

import android.app.Application
import android.content.Context

/**
 * @date 2020/5/9
 * @author zs
 */
class BaseApplication :Application() {


    override fun onCreate() {
        super.onCreate()
        baseApplication = this
    }

    companion object{
        private var baseApplication:BaseApplication? = null

        fun getContext(): Context {
            return baseApplication!!
        }
    }
}