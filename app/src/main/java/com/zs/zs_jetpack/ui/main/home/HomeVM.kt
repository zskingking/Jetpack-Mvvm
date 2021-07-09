package com.zs.zs_jetpack.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zs.base_library.base.BaseViewModel
import com.zs.base_wa_lib.article.ArticleListBean
import com.zs.zs_jetpack.ui.common.CollectRequest
import kotlinx.coroutines.async

/**
 * des 首页
 * @date 2020/6/22
 * @author zs
 */
class HomeVM : BaseViewModel() {

    private val repo by lazy { HomeRepo() }
    private val collectRequest by lazy { CollectRequest(_articleList) }
    /**
     * 文章列表
     */
    private val _articleList = MutableLiveData<MutableList<ArticleListBean>>()

    /**
     * 对外部提供只读的LiveData
     */
    val articleList: LiveData<MutableList<ArticleListBean>> = _articleList

    /**
     * banner
     */
    private val _banner = MutableLiveData<MutableList<BannerBean>>()

    /**
     * 对外部提供只读的LiveData
     */
    val banner: LiveData<MutableList<BannerBean>> = _banner

    /**
     * 获取banner
     */
    fun getBanner() {
        launch {
            _banner.value = repo.getBanner()
        }
    }

    /**
     * 获取首页文章列表， 包括banner
     */
    fun getArticle() {
        launch {
            val list = mutableListOf<ArticleListBean>()
            val articles = viewModelScope.async {
                repo.getArticles()
            }
            val topArticle = viewModelScope.async {
                repo.getTopArticles()
            }
            list.addAll(topArticle.await())
            list.addAll(articles.await())
            _articleList.value = list
        }
    }

    /**
     * 加载更多
     */
    fun loadMoreArticle() {
        launch {
            val list = _articleList.value
            list?.addAll(repo.loadMoreArticles())
            _articleList.value = list
            handleList(_articleList)
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