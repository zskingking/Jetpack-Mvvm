package com.zs.zs_jetpack.ui.my

import android.os.Bundle
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.base.DataBindingConfig
import com.zs.zs_jetpack.R

/**
 * des 我的文章
 * @author zs
 * @data 2020/7/12
 */
class MyFragment :BaseVmFragment(){


    override fun init(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId() = R.layout.fragment_my

    override fun getDataBindingConfig(): DataBindingConfig? {
        return null
    }
}