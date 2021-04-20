package com.zs.zs_jetpack.ui.register

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseRepository
import com.zs.base_library.http.ApiException
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager
import kotlinx.coroutines.CoroutineScope

/**
 * des 登陆
 * @date 2020/7/9
 * @author zs
 */
class RegisterRepo(coroutineScope: CoroutineScope, errorLiveData: MutableLiveData<ApiException>) :
    BaseRepository(coroutineScope, errorLiveData) {

    fun register(username: String, password: String, rePassword: String, registerLiveData : MutableLiveData<Any>) {
        launch(
            block = {
                RetrofitManager.getApiService(ApiService::class.java)
                    .register(username,password,rePassword)
                    .data()
            },
            success = {
                registerLiveData.postValue(it)
            }
        )
    }

}