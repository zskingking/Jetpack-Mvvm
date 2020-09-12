package com.zs.zs_jetpack.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zs.zs_jetpack.bean.ArticleListBean
import com.zs.zs_jetpack.common.BasePageVM
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
        viewModelScope.launch {
            repo.getBanner()
                .catch {
                    errorLiveData.postValue(getApiException(it))
                }
                .collect {
                    _banner.postValue(it)
                }
        }
    }

    /**
     * 获取首页文章列表， 包括banner
     */
    fun getArticle() {
        viewModelScope.launch {
            repo.getArticle()
                .catch {
                    errorLiveData.postValue(getApiException(it))
                }
                .collect {
                    _articleList.postValue(it)
                }
        }
    }

    /**
     * 加载更多
     */
    fun loadMoreArticle() {
        viewModelScope.launch {
            repo.loadMoreArticle()
                .catch {
                    errorLiveData.postValue(getApiException(it))
                }
                .collect {
                    articleList.value?.apply {
                        addAll(it)
                    }.let {
                        _articleList.postValue(it)
                    }
                }
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