package com.zs.zs_jetpack.ui.main.home

import android.util.Log
import androidx.databinding.ObservableField
import com.zs.base_library.base.BaseViewModel

/**
 * des 首页
 * @date 2020/6/22
 * @author zs
 */
class HomeVM :BaseViewModel(){

    val title = ObservableField<String>()

    fun setTitle(){
        title.set("我变成了music wanandroid")
    }
}