package com.zs.zs_jetpack.ui.set

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zs.base_library.base.BaseViewModel

/**
 * des 设置
 * @date 2020/7/10
 * @author zs
 */
class SetVM :BaseViewModel(){

    private val repo by lazy { SetRepo(viewModelScope,errorLiveData) }

    val logoutLiveData = MutableLiveData<Any>()

    fun logout(){
        repo.logout(logoutLiveData)
    }
}