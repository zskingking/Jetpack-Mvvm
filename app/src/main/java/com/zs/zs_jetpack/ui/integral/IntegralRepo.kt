package com.zs.zs_jetpack.ui.integral

import com.zs.base_library.base.BaseRepository
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager

/**
 * des 积分
 * @date 2020/7/8
 * @author zs
 */
class IntegralRepo : BaseRepository() {

    private var page = 1

    /**
     * 获取积分
     */
    suspend fun getIntegral() = withIO {
        page = 1
        RetrofitManager.getApiService(ApiService::class.java)
            .getIntegralRecord(page)
            .data()
            .let {
                IntegralListBean.trans(it.datas?: mutableListOf())
            }
    }

    /**
     * 获取下一页积分
     */
    suspend fun loadMore() = withIO {
        page++
        RetrofitManager.getApiService(ApiService::class.java)
            .getIntegralRecord(page)
            .data()
            .let {
                IntegralListBean.trans(it.datas?: mutableListOf())
            }
    }
}