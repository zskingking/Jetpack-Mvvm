package com.zs.zs_jetpack.ui.main.square.system

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zs.base_library.base.BaseViewModel

/**
 * des 体系
 * @date 2020/7/10
 * @author zs
 */
class SystemVM :BaseViewModel(){

    private val repo by lazy { SystemRepo(viewModelScope,errorLiveData) }
    /**
     * 体系列表数据
     */
    val systemLiveData = MutableLiveData<MutableList<SystemBean>>()

    fun getList(){
        repo.getList(systemLiveData)
    }
}