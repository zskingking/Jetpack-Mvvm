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

    private val repo by lazy { SetRepo() }

    val logoutLiveData = MutableLiveData<Any>()

    fun logout(){
        launch {
            logoutLiveData.value =  repo.logout()
        }
    }
}