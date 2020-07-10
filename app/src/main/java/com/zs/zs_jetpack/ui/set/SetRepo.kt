package com.zs.zs_jetpack.ui.set

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseRepository
import com.zs.base_library.http.ApiException
import com.zs.base_library.utils.PrefUtils
import com.zs.zs_jetpack.constants.Constants
import com.zs.zs_jetpack.event.LogoutEvent
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager
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
                //发送退出登录消息
                EventBus.getDefault().post(LogoutEvent())
                //重置登陆状态
                PrefUtils.setBoolean(Constants.LOGIN, false)
                //移除用户信息
                PrefUtils.removeKey(Constants.USER_INFO)

                logoutLiveData.postValue(it)
            }
        )
    }
}