package com.zs.zs_jetpack.ui.collect

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseRepository
import com.zs.base_library.common.isListEmpty
import com.zs.base_library.common.toast
import com.zs.base_library.http.ApiException
import com.zs.zs_jetpack.bean.ArticleBean
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager
import kotlinx.coroutines.CoroutineScope

/**
 * des 收藏的文章
 * @date 2020/7/8
 * @author zs
 */
class CollectRepo(coroutineScope: CoroutineScope, errorLiveData: MutableLiveData<ApiException>) :
    BaseRepository(coroutineScope, errorLiveData) {

    private var page = 0

    /**
     * 搜索
     */
    fun getCollect(
        isRefresh: Boolean
        , articleLiveData: MutableLiveData<MutableList<CollectBean.DatasBean>>
        , emptyLiveData: MutableLiveData<Any>
    ) {
        launch(
            block = {
                if (isRefresh) {
                    page = 0
                } else {
                    page++
                }
                RetrofitManager.getApiService(ApiService::class.java)
                    .getCollectData(page)
                    .data()
            },
            success = {
                //处理刷新/分页数据
                articleLiveData.value.apply {
                    //第一次加载 或 刷新 给 articleLiveData 赋予一个空集合
                    val currentList = if (isRefresh || this == null) {
                        mutableListOf()
                    } else {
                        this
                    }
                    it.datas?.let { it1 -> currentList.addAll(it1) }

                    articleLiveData.postValue(currentList)
                }
                if (isListEmpty(it.datas)) {
                    //第一页并且数据为空
                    if (page == 0) {
                        emptyLiveData.postValue(Any())
                    } else {
                        toast("没有数据啦～")
                    }
                }
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