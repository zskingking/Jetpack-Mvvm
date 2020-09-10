package com.zs.zs_jetpack.ui.integral

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseRepository
import com.zs.base_library.common.isListEmpty
import com.zs.base_library.common.toast
import com.zs.base_library.http.ApiException
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager
import kotlinx.coroutines.CoroutineScope

/**
 * des 收藏的文章
 * @date 2020/7/8
 * @author zs
 */
class IntegralRepo(coroutineScope: CoroutineScope, errorLiveData: MutableLiveData<ApiException>) :
    BaseRepository(coroutineScope, errorLiveData) {

    private var page = 1

    /**
     * 获取积分
     */
    fun getIntegral(
        isRefresh: Boolean
        , integralLiveData: MutableLiveData<MutableList<IntegralListBean>>
        , emptyLiveData: MutableLiveData<Any>
    ) {
        launch(
            block = {
                if (isRefresh) {
                    page = 1
                } else {
                    page++
                }
                RetrofitManager.getApiService(ApiService::class.java)
                    .getIntegralRecord(page)
                    .data()
            },
            success = {
                //处理刷新/分页数据
                integralLiveData.value.apply {
                    //第一次加载 或 刷新 给 articleLiveData 赋予一个空集合
                    val currentList = if (isRefresh || this == null) {
                        mutableListOf()
                    } else {
                        this
                    }
                    it.datas?.let { it1 -> currentList.addAll(IntegralListBean.trans(it1)) }
                    integralLiveData.postValue(currentList)
                }
                if (isListEmpty(it.datas)) {
                    //第一页并且数据为空
                    if (page == 1) {
                        emptyLiveData.postValue(Any())
                    } else {
                        toast("没有数据啦～")
                    }
                }
            }
        )
    }


}