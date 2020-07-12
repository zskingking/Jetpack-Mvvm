package com.zs.zs_jetpack.ui.publish

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseRepository
import com.zs.base_library.http.ApiException
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager
import kotlinx.coroutines.CoroutineScope

/**
 * des 发布
 * @author zs
 * @data 2020/7/12
 */
class PublishRepo(coroutineScope: CoroutineScope, errorLiveData: MutableLiveData<ApiException>) :
    BaseRepository(coroutineScope, errorLiveData) {


    /**
     * 发布
     * @param title 文章标题
     * @param link  文章链接
     */
    fun publish(title:String,link:String,publishLiveData : MutableLiveData<Any>){
        launch(
            block = {
                RetrofitManager.getApiService(ApiService::class.java)
                    .publishArticle(title,link)
                    .data(Any::class.java)
            },
            success = {
                publishLiveData.postValue(it)
            }
        )
    }
}