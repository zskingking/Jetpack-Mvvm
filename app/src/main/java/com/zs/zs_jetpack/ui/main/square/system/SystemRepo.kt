package com.zs.zs_jetpack.ui.main.square.system

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseRepository
import com.zs.base_library.http.ApiException
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager
import kotlinx.coroutines.CoroutineScope

/**
 * @date 2020/7/10
 * @author zs
 */
class SystemRepo(coroutineScope: CoroutineScope, errorLiveData: MutableLiveData<ApiException>) :
    BaseRepository(coroutineScope, errorLiveData) {

    fun getList(systemLiveData : MutableLiveData<MutableList<SystemBean>>){
       launch(
           block = {
               RetrofitManager.getApiService(ApiService::class.java)
                   .getSystemList()
                   .data()
           },
           success = {
                systemLiveData.postValue(it)
           }
       )
    }
}