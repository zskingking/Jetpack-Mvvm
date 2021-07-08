package com.zs.zs_jetpack.ui.main.mine

import com.zs.base_library.base.BaseRepository
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager

/**
 * des 我的
 * @date 2020/7/10
 * @author zs
 */
class MineRepo: BaseRepository() {

    suspend fun getInternal() = withIO {
        RetrofitManager.getApiService(ApiService::class.java)
            .getIntegral()
            .data()
    }

}