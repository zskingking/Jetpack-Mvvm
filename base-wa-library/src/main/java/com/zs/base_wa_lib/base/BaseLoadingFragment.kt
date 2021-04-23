package com.zs.base_wa_lib.base

import android.content.Context
import android.view.ViewGroup
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.utils.StatusUtils
import com.zs.base_wa_lib.view.LoadingTip

/**
 * @author zs
 * @date 2021/4/21
 */
abstract class BaseLoadingFragment : BaseVmFragment() {

    protected var gloding: LoadingTip? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseLoadingActivity) {
            gloding = context.loadingTip
        }
    }

    /**
     * 设置loadingView上下变局
     */
    protected fun setLoadingMargin(topMargin: Int, bottomMargin: Int) {
        val loadMarginTop = StatusUtils.getStatusBarHeight(mActivity) +topMargin
        val loadMarginBottom =  StatusUtils.getNavigationBarHeight(mActivity) + bottomMargin
        val params = gloding?.layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin = loadMarginTop
        params.bottomMargin = loadMarginBottom
        gloding?.layoutParams = params
    }
}