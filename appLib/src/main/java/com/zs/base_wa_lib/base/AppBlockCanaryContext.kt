package com.zs.base_wa_lib.base

import android.content.Context
import android.util.Log
import com.github.moduth.blockcanary.BlockCanaryContext
import com.github.moduth.blockcanary.internal.BlockInfo


/**
 * des
 * author zs
 * date 2021/6/24
 */
class AppBlockCanaryContext : BlockCanaryContext() {

    /**
     * 指定的卡顿阀值 100毫秒
     */
    override fun provideBlockThreshold(): Int {
        return 30
    }

    /**
     * 保存日志的路径
     */
    override fun providePath(): String {
        return "/blockcanary/"
    }

    /**
     * 是否需要在通知栏通知卡顿
     */
    override fun displayNotification(): Boolean {
        return true
    }

    /**
     * 此处可收集堆栈信息，以备上传分析
     * Block interceptor, developer may provide their own actions.
     */
    override fun onBlock(context: Context?, blockInfo: BlockInfo) {
        Log.i("zszs", "blockInfo $blockInfo")
    }


}