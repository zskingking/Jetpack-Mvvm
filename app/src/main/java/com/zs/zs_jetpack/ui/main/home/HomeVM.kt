package com.zs.zs_jetpack.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zs.zs_jetpack.bean.ArticleListBean
import com.zs.zs_jetpack.common.BasePageVM
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * des 首页
 * @date 2020/6/22
 * @author zs
 */
class HomeVM : BasePageVM() {

    private val repo by lazy { HomeRepo(viewModelScope, errorLiveData) }

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
        }
    }

    /**
     * 收藏
     */
    fun collect(id: Int) {
        viewModelScope.launch {
            _articleList.value?.let {
                repo.collect(id, it)
                    .catch {
                        errorLiveData.postValue(getApiException(it))
                    }
                    .collect { result ->
                        _articleList.postValue(result)
                    }
            }
        }
    }

    /**
     * 取消收藏
     */
    fun unCollect(id: Int) {
        viewModelScope.launch {
            _articleList.value?.let {
                repo.unCollect(id, it)
                    .catch {
                        errorLiveData.postValue(getApiException(it))
                    }
                    .collect { result ->
                        _articleList.postValue(result)
                    }
            }
        }
    }

}