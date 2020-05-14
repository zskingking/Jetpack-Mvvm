package com.zs.base_library.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParseException
import com.zs.base_library.http.ApiException
import com.zs.base_library.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * des 基础vm
 * @date 2020/5/13
 * @author zs
 */


open class BaseViewModel:ViewModel() {

    /**
     * 错误信息liveData
     */
    val errorLiveData = MutableLiveData<String>()

    /**
     * 对协程进行封装,统一处理错误信息
     *
     * @param block   执行中
     * @param success 执行成功
     * @param error   执行出错
     */
    protected fun <T>launch(block: suspend () -> T
                            ,success: suspend (T)->Unit
                            ,error: suspend (String)->Unit): Job {
        return viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    block()
                }
            }.onSuccess {
                success(it)
            }.onFailure {
                getException(it).apply {
                    //toast错误信息
                    toast(this)
                    //错误响应
                    error(this)
                    //统一做错误通知
                    errorLiveData.value = this
                }
            }
        }
    }


    /**
     * 错误信息处理
     */
    private fun getException(e: Throwable):String{
        return if (e is UnknownHostException) {
            "网络异常"
        } else if (e is JSONException || e is JsonParseException) {
            "数据异常"
        } else if (e is SocketTimeoutException) {
            "连接超时"
        } else if (e is ConnectException) {
            "连接错误"
        } else if (e is ApiException){
            e.errorMessage
        } else{
            "未知错误"
        }
    }
}