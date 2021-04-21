package com.zs.zs_jetpack.ui.integral

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zs.base_library.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

/**
 * des 文章vm
 * @date 2020/7/8
 * @author zs
 */
class IntegralVM : BaseViewModel() {
    private val repo by lazy { IntegralRepo() }

    /**
     * 收藏的的文章
     */
    val integralLiveData = MutableLiveData<MutableList<IntegralListBean>>()

    /**
     * 获取收藏列表
     */
    fun getIntegral() {
        launch {
            integralLiveData.value = repo.getIntegral()
            handleList(integralLiveData)
        }
    }

    /**
     * 获取收藏列表
     */
    fun loadMore() {
        launch {
            val list =  integralLiveData.value
            list?.addAll(repo.loadMore())
            integralLiveData.value = list
            handleList(integralLiveData)
        }
    }
}