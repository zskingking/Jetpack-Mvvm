package com.zs.zs_jetpack.ui.publish

import com.zs.base_library.base.BaseRepository
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager

/**
 * des 发布
 * @author zs
 * @data 2020/7/12
 */
class PublishRepo: BaseRepository() {

    /**
     * 发布
     * @param title 文章标题
     * @param link  文章链接
     */
    suspend fun publish(title:String,link:String) = withIO {
        RetrofitManager.getApiService(ApiService::class.java)
            .publishArticle(title,link)
            .data(Any::class.java)
    }
}