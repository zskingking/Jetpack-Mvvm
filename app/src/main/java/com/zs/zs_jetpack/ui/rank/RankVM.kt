package com.zs.zs_jetpack.ui.rank

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseViewModel

/**
 * des 排名
 * @date 2020/7/13
 * @author zs
 */
class RankVM :BaseViewModel(){

    private val repo by lazy { RankRepo() }

    val rankLiveData = MutableLiveData<MutableList<RankBean.DatasBean>>()

    /**
     * 获取排名
     */
    fun getRank(){
        launch {
            rankLiveData.value = repo.getRank()
        }
    }

    fun loadMore(){
        launch {
            val list = rankLiveData.value
            list?.addAll(repo.loadMore())
            rankLiveData.value = list
            handleList(rankLiveData)
        }
    }
}