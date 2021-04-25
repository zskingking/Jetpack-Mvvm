package com.zs.zs_jetpack.ui.main.tab

import com.zs.base_library.base.BaseRepository
import com.zs.zs_jetpack.constants.Constants
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager

/**
 * des tab
 * @date 2020/7/7
 * @author zs
 */
class TabRepo : BaseRepository() {


    suspend fun getTab(type: Int) = withIO {
        if (type == Constants.PROJECT_TYPE) {
            RetrofitManager.getApiService(ApiService::class.java)
                .getProjectTabList()
                .data()
        } else {
            RetrofitManager.getApiService(ApiService::class.java)
                .getAccountTabList()
                .data()
        }
    }
}
