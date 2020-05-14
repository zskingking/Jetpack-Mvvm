package com.zs.zs_jetpack.http

import com.zs.base_library.http.ApiResponse
import com.zs.zs_jetpack.bean.Article
import retrofit2.http.GET

/**
 * @date 2020/5/9
 * @author zs
 */
interface ApiService {

    @GET("/article/top/json")
    suspend fun getTopArticleList(): ApiResponse<MutableList<Article>>
}