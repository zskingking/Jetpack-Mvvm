package com.zs.zs_jetpack.ui.set

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseRepository
import com.zs.base_library.http.ApiException
import com.zs.base_library.utils.PrefUtils
import com.zs.zs_jetpack.constants.Constants
import com.zs.zs_jetpack.event.LogoutEvent
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager
import com.zs.zs_jetpack.utils.CacheUtil
import kotlinx.coroutines.CoroutineScope
import org.greenrobot.eventbus.EventBus

/**
 * des 设置
 * @date 2020/7/10
 * @author zs
 */
class SetRepo(coroutineScope: CoroutineScope, errorLiveData: MutableLiveData<ApiException>) :
    BaseRepository(coroutineScope, errorLiveData) {

    /**
     * 退出登陆
     */
    fun logout(logoutLiveData : MutableLiveData<Any>){
        launch(
            block = {
                RetrofitManager.getApiService(ApiService::class.java)
                    .logout()
                    .data(Any::class.java)
            },
            success = {
                CacheUtil.resetUser()
                logoutLiveData.postValue(it)
            }
        )
    }
}