package com.zs.zs_jetpack

import com.zs.base_library.BaseApp
import com.zs.zs_jetpack.play.PlayerManager

/**
 * @author zs
 * @data 2020/6/26
 */
class App: BaseApp() {

    override fun onCreate() {
        super.onCreate()
        PlayerManager.instance.init(this)
    }
}