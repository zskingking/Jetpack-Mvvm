package com.zs.zs_jetpack.ui.web

import androidx.databinding.ObservableField
import com.zs.base_library.base.BaseViewModel

/**
 * @date 2020/7/9
 * @author zs
 */
class WebVM:BaseViewModel() {

    /**
     * webView 进度
     */
    val progress = ObservableField<Int>()


    /**
     * 最大 进度
     */
    val maxProgress = ObservableField<Int>()

    /**
     * progress是否隐藏
     */
    val isVisible = ObservableField<Boolean>()

}