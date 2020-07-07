package com.zs.zs_jetpack.ui.main.tab

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseRepository
import com.zs.base_library.http.ApiException
import com.zs.zs_jetpack.constants.Constants
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager
import kotlinx.coroutines.CoroutineScope

/**
 * des tab
 * @date 2020/7/7
 * @author zs
 */
class TabRepo(coroutineScope: CoroutineScope, errorLiveData: MutableLiveData<ApiException>) :
    BaseRepository(coroutineScope, errorLiveData) {


    fun getTab(type: Int, tabLiveData: MutableLiveData<MutableList<TabBean>>) {
        launch(
            block = {
                if (type == Constants.PROJECT_TYPE) {
                    RetrofitManager.getApiService(ApiService::class.java)
                        .getProjectTabList()
                        .data()
                } else {
                    RetrofitManager.getApiService(ApiService::class.java)
                        .getAccountTabList()
                        .data()
                }
            },
            success = {
                tabLiveData.postValue(it)
            })

    }
}
