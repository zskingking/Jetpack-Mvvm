package com.zs.base_wa_lib.base

import android.content.Context
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_wa_lib.view.LoadingTip

/**
 * @author zs
 * @date 2021/4/21
 */
abstract class BaseLoadingFragment : BaseVmFragment() {

    protected var loadingTip: LoadingTip? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseLoadingActivity) {
            loadingTip = context.loadingTip
        }
    }
}