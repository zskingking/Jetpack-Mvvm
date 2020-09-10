package com.zs.zs_jetpack.ui.main.square.system

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseRepository
import com.zs.base_library.common.isListEmpty
import com.zs.base_library.common.toast
import com.zs.base_library.http.ApiException
import com.zs.zs_jetpack.bean.ArticleBean
import com.zs.zs_jetpack.bean.ArticleListBean
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager
import kotlinx.coroutines.CoroutineScope

/**
 * @date 2020/7/10
 * @author zs
 */
class SystemRepo(coroutineScope: CoroutineScope, errorLiveData: MutableLiveData<ApiException>) :
    BaseRepository(coroutineScope, errorLiveData) {

    /**
     * 获取体系列表
     */
    fun getSystemList(systemLiveData : MutableLiveData<MutableList<SystemBean>>){
       launch(
           block = {
               RetrofitManager.getApiService(ApiService::class.java)
                   .getSystemList()
                   .data()
           },
           success = {
                systemLiveData.postValue(it)
           }
       )
    }

    /**
     * 页码
     */
    private var page = 0
    /**
     * 获取体系对应的
     */
    fun getArticleList(isRefresh:Boolean,id:Int,articleLiveData
    : MutableLiveData<MutableList<ArticleListBean>>){
        launch(
            block = {
                if (isRefresh){
                    page = 0
                }else{
                    page++
                }
                RetrofitManager.getApiService(ApiService::class.java)
                    .getSystemArticle(page,id)
                    .data()
            },
            success = {
                //处理刷新/分页数据
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

                if (isListEmpty(it.datas)) {
                    //第一页并且数据为空
                    if (page == 0) {
                        //预留
                    } else {
                        toast("没有数据啦～")
                    }
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