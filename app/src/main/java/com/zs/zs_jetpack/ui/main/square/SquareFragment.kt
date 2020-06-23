package com.zs.zs_jetpack.ui.main.square


import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.base.LazyVmFragment
import com.zs.zs_jetpack.R

/**
 * des 广场
 * @author zs
 * @date 2020-05-14
 */
class SquareFragment : LazyVmFragment() {

    override fun lazyInit() {
    }

    override fun getLayoutId(): Int? {
        return R.layout.fragment_square
    }

    override fun getDataBindingConfig(): DataBindingConfig? {
        return null
    }


}

