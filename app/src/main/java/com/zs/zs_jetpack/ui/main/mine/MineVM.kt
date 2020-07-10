package com.zs.zs_jetpack.ui.main.mine

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zs.base_library.base.BaseViewModel
import com.zs.wanandroid.entity.IntegralBean

/**
 * des 我的
 * @date 2020/7/10
 * @author zs
 */
class MineVM :BaseViewModel(){

    /**
     * 用户名
     */
    val username = ObservableField<String>().apply {
        set("请先登录")
    }

    /**
     * id
     */
    val id = ObservableField<String>().apply {
        set("---")
    }

    /**
     * 排名
     */
    val rank = ObservableField<String>().apply {
        set("0")
    }


    /**
     * 当前积分
     */
    val internal = ObservableField<String>().apply {
        set("0")
    }


    private val repo by lazy { MineRepo(viewModelScope,errorLiveData) }
    val internalLiveData = MutableLiveData<IntegralBean>()

    fun getInternal(){
        repo.getInternal(internalLiveData)
    }

}