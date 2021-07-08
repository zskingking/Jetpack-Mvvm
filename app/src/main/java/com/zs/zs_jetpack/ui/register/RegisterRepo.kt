package com.zs.zs_jetpack.ui.register

import com.zs.base_library.base.BaseRepository
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager

/**
 * des 登陆
 * @date 2020/7/9
 * @author zs
 */
class RegisterRepo : BaseRepository() {

    suspend fun register(username: String, password: String, rePassword: String) = withIO {
        RetrofitManager.getApiService(ApiService::class.java)
            .register(username,password,rePassword)
            .data()
    }

}