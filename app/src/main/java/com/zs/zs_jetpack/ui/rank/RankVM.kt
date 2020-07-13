package com.zs.zs_jetpack.ui.rank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zs.base_library.base.BaseViewModel

/**
 * des 排名
 * @date 2020/7/13
 * @author zs
 */
class RankVM :BaseViewModel(){

    private val repo by lazy { RankRepo(viewModelScope,errorLiveData) }

    val rankLiveData = MutableLiveData<MutableList<RankBean.DatasBean>>()

    /**
     * 获取排名
     */
    fun getRank(isRefresh:Boolean){
        repo.getRank(isRefresh,rankLiveData)
    }
}