package com.zs.base_wa_lib.base

import com.github.moduth.blockcanary.BlockCanary
import com.zs.base_library.BaseApp

/**
 * des
 * author zs
 * date 2021/6/24
 */
open class BaseWanAndroidApp : BaseApp() {

    override fun onCreate() {
        super.onCreate()
        BlockCanary.install(this, AppBlockCanaryContext()).start()
    }
}