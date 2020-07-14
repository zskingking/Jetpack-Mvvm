package com.zs.zs_jetpack.ui.collect

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zs.base_library.base.BaseViewModel
import com.zs.zs_jetpack.bean.ArticleBean

/**
 * des 文章vm
 * @date 2020/7/8
 * @author zs
 */
class CollectVM : BaseViewModel() {
    private val repo by lazy { CollectRepo(viewModelScope, errorLiveData) }


    /**
     * 收藏的的文章
     */
    val articleLiveData = MutableLiveData<MutableList<CollectBean.DatasBean>>()


    /**
     * 取消收藏
     */
    val unCollectLiveData = MutableLiveData<Int>()


    /**
     * 获取收藏列表
     */
    fun getCollect(isRefresh: Boolean) {
        repo.getCollect(
            isRefresh, articleLiveData, emptyLiveDate
        )
    }

    /**
     * 取消收藏
     */
    fun unCollect(id: Int) {
        repo.unCollect(id, unCollectLiveData)
    }
}