package com.zs.zs_jetpack.http

/**
 * des 实例化Retrofit,获取ApiService
 *
 * @author zs
 * @date 2020-05-09
 */
object RetrofitHelper {
    var apiService: ApiService? = null

    init {
        apiService =
            RetrofitFactory.factory().create(ApiService::class.java)
    }
}