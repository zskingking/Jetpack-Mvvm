package com.zs.zs_jetpack.ui.main.home

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseRepository
import com.zs.base_library.http.ApiException
import com.zs.wanandroid.entity.BannerBean
import com.zs.zs_jetpack.bean.ArticleBean
import com.zs.zs_jetpack.bean.ArticleListBean
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
        , articleLiveData: MutableLiveData<MutableList<ArticleListBean>>
        , banner: MutableLiveData<MutableList<BannerBean>>
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
    private fun getTopList(articleLiveData: MutableLiveData<MutableList<ArticleListBean>>) {
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

        articleLiveData: MutableLiveData<MutableList<ArticleListBean>>,
        list: MutableList<ArticleBean.DatasBean>? = null,
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
                    it.datas?.let { it1 -> currentList.addAll(ArticleListBean.trans(it1)) }
                    articleLiveData.postValue(currentList)
                }
            }
        )
    }

    /**
     * 获取banner
     */
    private fun getBanner(banner: MutableLiveData<MutableList<BannerBean>>) {
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

    /**
     * 收藏
     */
    fun collect(id:Int,collectLiveData : MutableLiveData<Int>){
        launch(
            block = {
                RetrofitManager.getApiService(ApiService::class.java)
                    .collect(id)
                    .data(Any::class.java)
            },
            success = {
                collectLiveData.postValue(id)
            }
        )
    }

    /**
     * 收藏
     */
    fun unCollect(id:Int,unCollectLiveData : MutableLiveData<Int>){
        launch(
            block = {
                RetrofitManager.getApiService(ApiService::class.java)
                    .unCollect(id)
                        //如果data可能为空,可通过此方式通过反射生成对象,避免空判断
                    .data(Any::class.java)
            },
            success = {
                unCollectLiveData.postValue(id)
            }
        )
    }
}
