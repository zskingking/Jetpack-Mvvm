package com.zs.base_library.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding

/**
 * des 基于androidx 实现懒加载
 * @date 2020/5/9
 * @author zs
 */
abstract class LazyVmFragment<BD:ViewDataBinding> :BaseVmFragment<BD>(){

    private var isLoaded = false
    override fun onResume() {
        super.onResume()
        //增加了Fragment是否可见的判断
        if (!isLoaded && !isHidden) {
            lazyInit()
            isLoaded = true
        }
    }

    override fun init(savedInstanceState: Bundle?) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
    }

    abstract fun lazyInit()

}