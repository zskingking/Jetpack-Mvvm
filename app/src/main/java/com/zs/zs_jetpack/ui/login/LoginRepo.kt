package com.zs.zs_jetpack.ui.login

import com.zs.base_library.base.BaseRepository
import com.zs.base_library.utils.PrefUtils
import com.zs.zs_jetpack.constants.Constants
import com.zs.base_wa_lib.event.LoginEvent
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager
import org.greenrobot.eventbus.EventBus

/**
 * des 登陆
 * @date 2020/7/9
 * @author zs
 */
class LoginRepo : BaseRepository() {

    suspend fun login(username: String, password: String) = withIO {
        RetrofitManager.getApiService(ApiService::class.java)
            .login(username,password)
            .data()
            .apply {
                //登陆成功保存用户信息，并发送消息
                PrefUtils.setObject(Constants.USER_INFO,this)
                //更改登陆状态
                PrefUtils.setBoolean(Constants.LOGIN,true)
                //发送登陆消息
                EventBus.getDefault().post(LoginEvent())
            }
    }

}