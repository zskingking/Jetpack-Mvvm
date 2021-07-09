package com.zs.zs_jetpack.ui.main.tab

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseViewModel
import com.zs.base_wa_lib.article.ArticleListBean
import com.zs.zs_jetpack.ui.common.CollectRequest

/**
 * des 文章vm
 * @date 2020/7/8
 * @author zs
 */
class ArticleVM : BaseViewModel() {


    private val repo by lazy { ArticleRepo() }
    private val collectRequest by lazy { CollectRequest(_articleLiveData) }

    /**
     * 体系列表数据
     */
    private val _articleLiveData = MutableLiveData<MutableList<ArticleListBean>>()
    val articleLiveData: LiveData<MutableList<ArticleListBean>> = _articleLiveData


    /**
     * 获取文章列表
     */
    fun getArticleList(type:Int,tabId:Int) {
        launch {
            _articleLiveData.value = repo.getArticles(type, tabId)
            handleList(_articleLiveData)
        }
    }

    /**
     * 获取文章列表
     */
    fun loadMoreArticleList(type:Int,tabId:Int) {
        launch {
            val list = _articleLiveData.value
            list?.addAll(repo.loadMoreArticles(type, tabId))
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

    override fun onCleared() {
        super.onCleared()
        Log.i("zszs","onCleared")
    }

}