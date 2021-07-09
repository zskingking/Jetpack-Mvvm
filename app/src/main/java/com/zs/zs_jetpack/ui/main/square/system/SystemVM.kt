package com.zs.zs_jetpack.ui.main.square.system

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseViewModel
import com.zs.base_wa_lib.article.ArticleListBean
import com.zs.zs_jetpack.ui.common.CollectRequest

/**
 * des 体系/体系列表
 * @date 2020/7/10
 * @author zs
 */
class SystemVM : BaseViewModel() {

    private val repo by lazy { SystemRepo() }
    private val collectRequest by lazy { CollectRequest(_articleLiveData) }

    /**
     * 体系列表数据
     */
    private val _systemLiveData = MutableLiveData<MutableList<SystemBean>>()
    val systemLiveData: LiveData<MutableList<SystemBean>> = _systemLiveData

    /**
     * 体系列表数据
     */
    private val _articleLiveData = MutableLiveData<MutableList<ArticleListBean>>()
    val articleLiveData: LiveData<MutableList<ArticleListBean>> = _articleLiveData

    /**
     * 获取体系列表
     */
    fun getSystemList() {
        launch {
            _systemLiveData.value = repo.getSystemList()
        }
    }

    /**
     * 获取文章列表
     */
    fun getArticleList(id: Int) {
        launch {
            _articleLiveData.value = repo.getArticleList(id)
            handleList(_articleLiveData)
        }
    }

    /**
     * 获取文章列表
     */
    fun loadMoreArticleList(id: Int) {
        launch {
            val list = _articleLiveData.value
            list?.addAll(repo.loadMoreArticle(id))
            _articleLiveData.value = list
            handleList(_articleLiveData)
        }
    }

    /**
     * 收藏
     */
    fun collect(id: Int) {
        launch {
            collectRequest.collect(id)
        }
    }

    /**
     * 取消收藏
     */
    fun unCollect(id: Int) {
        launch {
            collectRequest.unCollect(id)
        }
    }

}