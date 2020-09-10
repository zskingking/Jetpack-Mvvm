package com.zs.zs_jetpack.ui.main.tab

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseRepository
import com.zs.base_library.http.ApiException
import com.zs.zs_jetpack.bean.ArticleBean
import com.zs.zs_jetpack.bean.ArticleListBean
import com.zs.zs_jetpack.constants.Constants
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager
import kotlinx.coroutines.CoroutineScope

/**
 * des 每个tab下文章的数据层
 * @date 2020/7/8
 * @author zs
 */
class ArticleRepo(coroutineScope: CoroutineScope, errorLiveData: MutableLiveData<ApiException>) :
    BaseRepository(coroutineScope, errorLiveData) {

    private var page = 1

    /**
     * 获取文章
     * @param type 项目或者公号类型
     * @param tabId tabId
     * @param isRefresh 是否为刷新或第一次进入
     * @param articleLiveData 文章liveData
     */
    fun getArticle(
        type: Int,
        tabId: Int,
        isRefresh: Boolean,
        articleLiveData: MutableLiveData<MutableList<ArticleListBean>>
    ) {
        launch(
            block = {
                //是否为刷新
                if (isRefresh){
                    page = 1
                }else{
                    page++
                }
                //项目
                if (type == Constants.PROJECT_TYPE) {
                    RetrofitManager.getApiService(ApiService::class.java)
                        .getProjectList(page,tabId)
                        .data()
                }
                //公号
                else {
                    RetrofitManager.getApiService(ApiService::class.java)
                        .getAccountList(tabId,page)
                        .data()
                }
            },
            success = {
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
     * 取消收藏
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