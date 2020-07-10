package com.zs.zs_jetpack.utils

import com.zs.base_library.utils.PrefUtils
import com.zs.zs_jetpack.constants.Constants
import com.zs.zs_jetpack.event.LogoutEvent
import org.greenrobot.eventbus.EventBus

/**
 * des 缓存工具类
 * @date 2020/7/9
 * @author zs
 */
class CacheUtil {

    companion object {
        /**
         * 登录状态
         */
        fun isLogin(): Boolean {
            return PrefUtils.getBoolean(Constants.LOGIN, false)
        }

        /**
         * 退出登录，重置用户状态
         */
        fun resetUser() {
            //发送退出登录消息
            EventBus.getDefault().post(LogoutEvent())
            //更新登陆状态
            PrefUtils.setBoolean(Constants.LOGIN, false)
            //移除用户信息
            PrefUtils.removeKey(Constants.USER_INFO)
            //移除积分信息
            PrefUtils.removeKey(Constants.INTEGRAL_INFO)
        }
    }
}