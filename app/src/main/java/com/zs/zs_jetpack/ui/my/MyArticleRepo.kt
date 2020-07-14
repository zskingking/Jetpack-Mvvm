package com.zs.zs_jetpack.ui.my

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
 * des 我的文章
 * @date 2020/7/14
 * @author zs
 */
class MyArticleRepo(coroutineScope: CoroutineScope, errorLiveData: MutableLiveData<ApiException>) :
    BaseRepository(coroutineScope, errorLiveData) {

    private var page = 1

    fun getMyArticle(
        isRefresh: Boolean,
        myLiveDate: MutableLiveData<MutableList<ArticleBean.DatasBean>>,
        emptyLivedData: MutableLiveData<Any>)
    {
        launch(
            block = {
                if (isRefresh){
                    page = 1
                }else{
                    page++
                }
                RetrofitManager.getApiService(ApiService::class.java)
                    .getMyArticle(page)
                    .data()
            },
            success = {
                //处理刷新/分页数据
                myLiveDate.value.apply {
                    //第一次加载 或 刷新 给 articleLiveData 赋予一个空集合
                    val currentList = if (isRefresh || this == null){
                        mutableListOf()
                    }else{
                        this
                    }
                    it.shareArticles?.datas?.let { it1 -> currentList.addAll(it1) }
                    myLiveDate.postValue(currentList)
                }

                if (isListEmpty(it.shareArticles?.datas)) {
                    //第一页并且数据为空
                    if (page == 1) {
                        //预留
                        emptyLivedData.postValue(Any())
                    } else {
                        toast("没有数据啦～")
                    }
                }
            }
        )
    }
    fun delete(id:Int,deleteLiveData : MutableLiveData<Int>){
        launch(
            block = {
                RetrofitManager.getApiService(ApiService::class.java)
                    .deleteMyArticle(id)
                    .data(Any::class.java)
            },
            success = {
               deleteLiveData.postValue(id)
            }
        )

    }

}