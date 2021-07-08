package com.zs.zs_jetpack.ui.rank

import com.zs.base_library.base.BaseRepository
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager

/**
 * des 排名
 * @date 2020/7/13
 * @author zs
 */
class RankRepo : BaseRepository() {

    private var page = 1
    suspend fun getRank(): MutableList<RankBean.DatasBean> = withIO {
        page = 1
        RetrofitManager.getApiService(ApiService::class.java)
            .getRank(page)
            .data()
            .datas
    }

    suspend fun loadMore(): MutableList<RankBean.DatasBean> = withIO {
        page++
        RetrofitManager.getApiService(ApiService::class.java)
            .getRank(page)
            .data()
            .datas
    }
}