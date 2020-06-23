package com.zs.base_library.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * des 基础vm
 * @date 2020/5/13
 * @author zs
 */

open class BaseViewModel:ViewModel() {

    /**
     * 错误信息liveData
     */
    val errorLiveData = MutableLiveData<String>()
}