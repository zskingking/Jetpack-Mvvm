package com.zs.zs_jetpack.ui.login

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseViewModel

/**
 * des 登陆
 * @date 2020/7/9
 * @author zs
 */
class LoginVM :BaseViewModel(){

    /**
     * 用户名
     */
    val username = ObservableField<String>().apply {
        set("")
    }

    /**
     * 密码
     */
    val password = ObservableField<String>().apply {
        set("")
    }

    /**
     * 密码是否可见
     */
    val passIsVisibility = ObservableField<Boolean>().apply {
        set(false)
    }

    /**
     * 登陆
     */
    val loginLiveData = MutableLiveData<Any>()

    fun login(){

    }
}