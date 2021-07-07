package com.zs.zs_jetpack

import android.content.Context
import androidx.multidex.MultiDex
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.zs.base_wa_lib.base.BaseWanAndroidApp





/**
 * @author zs
 * @data 2020/6/26
 */
class App: BaseWanAndroidApp() {

    override fun onCreate() {
        super.onCreate()
        initSmartHead()
        MultiDex.install(this)
    }

    /**
     * 初始化加载刷新ui
     */
    private fun initSmartHead() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context: Context?, _: RefreshLayout? ->
            ClassicsHeader(context)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context: Context?, _: RefreshLayout? ->
            ClassicsFooter(context)
        }
    }
}