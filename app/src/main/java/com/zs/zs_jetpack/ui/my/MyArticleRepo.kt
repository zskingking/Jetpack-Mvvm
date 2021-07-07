package com.zs.zs_jetpack.ui.my

import com.zs.base_library.base.BaseRepository
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager

/**
 * des 我的文章
 * @date 2020/7/14
 * @author zs
 */
class MyArticleRepo : BaseRepository() {

    private var page = 1

    suspend fun getMyArticle() = withIO {
        page = 1
        RetrofitManager.getApiService(ApiService::class.java)
            .getMyArticle(page)
            .data()
            .shareArticles
            ?.datas
    }

    suspend fun loadMore() = withIO {
        page++
        RetrofitManager.getApiService(ApiService::class.java)
            .getMyArticle(page)
            .data()
            .shareArticles
            ?.datas
    }

    suspend fun delete(id: Int) = withIO {
        RetrofitManager.getApiService(ApiService::class.java)
            .deleteMyArticle(id)
            .data(Any::class.java)
    }

}