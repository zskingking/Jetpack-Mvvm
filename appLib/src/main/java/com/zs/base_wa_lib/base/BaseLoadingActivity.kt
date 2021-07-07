package com.zs.base_wa_lib.base

import android.os.Bundle
import android.widget.FrameLayout
import com.zs.base_library.base.BaseVmActivity
import com.zs.base_library.common.dip2px
import com.zs.base_library.utils.StatusUtils
import com.zs.base_wa_lib.view.LoadingTip

/**
 * des 带loading的Activity
 * author zs
 * date 2021/4/21
 */
abstract class BaseLoadingActivity : BaseVmActivity() {
    private var decorView: FrameLayout? = null
    var loadingTip: LoadingTip? = null

    override fun init(savedInstanceState: Bundle?) {
        decorView = window.decorView as FrameLayout
        val loadMarginTop = StatusUtils.getStatusBarHeight(this) + dip2px(this, 50f)
        val loadMarginBottom =  StatusUtils.getNavigationBarHeight(this)
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        params.topMargin = loadMarginTop
        params.bottomMargin = loadMarginBottom
        loadingTip = LoadingTip(this)
        decorView?.addView(loadingTip, params)
        init2(savedInstanceState)
    }

    abstract fun init2(savedInstanceState: Bundle?)
}