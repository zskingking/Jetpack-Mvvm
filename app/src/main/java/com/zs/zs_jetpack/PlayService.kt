package com.zs.zs_jetpack

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.zs.zs_jetpack.play.PlayerManager

/**
 * des 播放Service，是一个空壳，只做播放初始化
 * @author zs
 * @data 2020/7/10
 */
class PlayService:Service() {

    override fun onCreate() {
        super.onCreate()
        PlayerManager.instance.init(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        //释放播放器
        PlayerManager.instance.clear()
    }
}