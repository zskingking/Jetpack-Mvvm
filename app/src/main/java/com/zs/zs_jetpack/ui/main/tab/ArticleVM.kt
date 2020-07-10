package com.zs.zs_jetpack.ui.main.tab

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zs.base_library.base.BaseViewModel
import com.zs.zs_jetpack.bean.ArticleBean

/**
 * des 文章vm
 * @date 2020/7/8
 * @author zs
 */
class ArticleVM : BaseViewModel() {

    private val repo by lazy { ArticleRepo(viewModelScope,errorLiveData) }
    val articleLiveData = MutableLiveData<MutableList<ArticleBean.DatasBean>>()
    /**
     * 获取文章
     */
    fun getArticle(type: Int, tabId: Int, isRefresh: Boolean) {
        repo.getArticle(type,tabId,isRefresh,articleLiveData)
    }
}