package com.zs.base_library.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.base_library.http.ApiException

/**
 * des 基础vm
 * @date 2020/5/13
 * @author zs
 */

open class BaseViewModel:ViewModel() {

    /**
     * 错误信息liveData
     */
    val errorLiveData = MutableLiveData<ApiException>()

    /**
     * 无更多数据
     */
    val footLiveDate = MutableLiveData<Any>()

    /**
     * 无数据
     */
    val emptyLiveDate = MutableLiveData<Any>()
}