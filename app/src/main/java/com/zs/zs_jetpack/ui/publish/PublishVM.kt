package com.zs.zs_jetpack.ui.publish

import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zs.base_library.base.BaseViewModel
import com.zs.base_library.common.toast

/**
 * des 发布文章
 * @author zs
 * @data 2020/7/12
 */
class PublishVM :BaseViewModel(){

    /**
     * 文章标题
     */
    val articleTitle = ObservableField<String>().apply { set("") }

    /**
     * 文章链接
     */
    val articleLink = ObservableField<String>().apply { set("") }

    /**
     * 发布文章
     */
    val publishLiveData = MutableLiveData<Any>()

    private val repo by lazy { PublishRepo(viewModelScope,errorLiveData) }

    fun publish(){
        if (TextUtils.isEmpty(articleTitle.get())||TextUtils.isEmpty(articleLink.get())){
            toast("请输入标题跟链接～")
        }else{
            repo.publish(articleTitle.get()!!,articleLink.get()!!,publishLiveData)
        }
    }
}