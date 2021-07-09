package com.zs.base_wa_lib.utils

import com.zs.base_library.utils.PrefUtils
import com.zs.base_wa_lib.constants.AppLibConstants
import com.zs.base_wa_lib.event.LogoutEvent
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
            return PrefUtils.getBoolean(AppLibConstants.LOGIN, false)
        }

        /**
         * 退出登录，重置用户状态
         */
        fun resetUser() {
            //发送退出登录消息
            EventBus.getDefault().post(LogoutEvent())
            //更新登陆状态
            PrefUtils.setBoolean(AppLibConstants.LOGIN, false)
            //移除用户信息
            PrefUtils.removeKey(AppLibConstants.USER_INFO)
            //移除积分信息
            PrefUtils.removeKey(AppLibConstants.INTEGRAL_INFO)
        }
    }
}