package com.zs.zs_jetpack.ui.my

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zs.base_library.base.BaseViewModel
import com.zs.zs_jetpack.bean.ArticleBean

/**
 * @date 2020/7/13
 * @author zs
 */
class MyArticleVM :BaseViewModel(){

    private val repo by lazy { MyArticleRepo(viewModelScope,errorLiveData) }

    val myLiveDate = MutableLiveData<MutableList<ArticleBean.DatasBean>>()

    fun getMyArticle(isRefresh:Boolean){
        repo.getMyArticle(isRefresh,myLiveDate,emptyLiveDate)
    }
}