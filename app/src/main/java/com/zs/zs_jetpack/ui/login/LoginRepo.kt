package com.zs.zs_jetpack.ui.login

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseRepository
import com.zs.base_library.http.ApiException
import kotlinx.coroutines.CoroutineScope

/**
 * des 登陆
 * @date 2020/7/9
 * @author zs
 */
class LoginRepo(coroutineScope: CoroutineScope, errorLiveData: MutableLiveData<ApiException>) :
    BaseRepository(coroutineScope, errorLiveData) {

}