package com.zs.zs_jetpack.ui.main.mine

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseViewModel
import com.zs.base_library.utils.PrefUtils
import com.zs.zs_jetpack.constants.Constants
import com.zs.zs_jetpack.utils.CacheUtil

/**
 * des 我的
 * @date 2020/7/10
 * @author zs
 */
class MineVM : BaseViewModel() {

    /**
     * 用户名
     */
    val username = ObservableField<String>().apply {
        set("请先登录")
    }

    /**
     * id
     */
    val id = ObservableField<String>().apply {
        set("---")
    }

    /**
     * 排名
     */
    val rank = ObservableField<String>().apply {
        set("0")
    }

    /**
     * 当前积分
     */
    val internal = ObservableField<String>().apply {
        set("0")
    }

    private val repo by lazy { MineRepo() }
    val internalLiveData = MutableLiveData<IntegralBean>()

    fun getInternal() {
        launch {
            var integralBean:IntegralBean? = null
            PrefUtils.getObject(Constants.INTEGRAL_INFO)?.let {
                //先从本地获取积分，获取不到再通过网络获取
                integralBean = it as IntegralBean?
            }
            if (integralBean == null) {
                if (CacheUtil.isLogin()) {
                    val data = repo.getInternal()
                    setIntegral(data)
                    PrefUtils.setObject(Constants.INTEGRAL_INFO,data)
                }
            } else {
                setIntegral(integralBean)
            }
        }
    }

    private fun setIntegral(integralBean:IntegralBean?){
        integralBean?.let  { it ->
            //通过dataBinDing与View绑定
            username.set(it.username)
            id.set("${it.userId}")
            rank.set("${it.rank}")
            internal.set("${it.coinCount}")
            internalLiveData.value = it
        }
    }

}