package com.zs.zs_jetpack.ui.set

import com.zs.base_library.base.BaseRepository
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager
import com.zs.base_wa_lib.utils.CacheUtil

/**
 * des 设置
 * @date 2020/7/10
 * @author zs
 */
class SetRepo : BaseRepository() {

    /**
     * 退出登陆
     */
    suspend fun logout() = withIO {
        RetrofitManager.getApiService(ApiService::class.java)
            .logout()
            .data(Any::class.java)
            .apply {
                CacheUtil.resetUser()
            }
    }
}