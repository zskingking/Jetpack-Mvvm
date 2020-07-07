package com.zs.zs_jetpack.ui.main.home

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseRepository
import com.zs.base_library.http.ApiException
import com.zs.wanandroid.entity.BannerEntity
import com.zs.zs_jetpack.bean.ArticleEntity
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager
import kotlinx.coroutines.CoroutineScope

/**
 * des 首页
 * @date 2020/7/6
 * @author zs
 */
class HomeRepo(coroutineScope: CoroutineScope, errorLiveData: MutableLiveData<ApiException>) :
    BaseRepository(coroutineScope, errorLiveData) {

    private var page = 0

    /**
     * 获取首页文章列表， 包括banner
     */
    fun getArticleList(
        isRefresh: Boolean
        , articleLiveData: MutableLiveData<MutableList<ArticleEntity.DatasBean>>
        , banner: MutableLiveData<MutableList<BannerEntity>>
    ) {
        //仅在第一页或刷新时调用banner和置顶
        if (isRefresh) {
            page = 0
            getBanner(banner)
            getTopList(articleLiveData)
        } else {
            page++
            getHomeList(articleLiveData)
        }
    }

    /**
     * 获取置顶文章
     */
    private fun getTopList(articleLiveData: MutableLiveData<MutableList<ArticleEntity.DatasBean>>) {
        launch(
            block = {
                RetrofitManager.getApiService(ApiService::class.java)
                    .getTopList()
                    .data()
            },
            success = {
                getHomeList(articleLiveData, it, true)
            }
        )
    }

    /**
     * 获取首页文章
     */
    private fun getHomeList(

        articleLiveData: MutableLiveData<MutableList<ArticleEntity.DatasBean>>,
        list: MutableList<ArticleEntity.DatasBean>? = null,
        isRefresh: Boolean = false
    ) {
        launch(
            block = {
                RetrofitManager.getApiService(ApiService::class.java)
                    .getHomeList(page)
                    .data()
            },
            success = {
                list?.let { list ->
                    it.datas?.addAll(0, list)
                }
                //做数据累加
                articleLiveData.value.apply {

                    //第一次加载 或 刷新 给 articleLiveData 赋予一个空集合
                    val currentList = if (isRefresh || this == null){
                        mutableListOf()
                    }else{
                        this
                    }
                    it.datas?.let { it1 -> currentList.addAll(it1) }
                    articleLiveData.postValue(currentList)
                }
            }
        )
    }

    /**
     * 获取banner
     */
    private fun getBanner(banner: MutableLiveData<MutableList<BannerEntity>>) {
        launch(
            block = {
                RetrofitManager.getApiService(ApiService::class.java)
                    .getBanner()
                    .data()
            },
            success = {
                banner.postValue(it)
            }
        )
    }
}
