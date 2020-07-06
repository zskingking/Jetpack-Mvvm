package com.zs.base_library.base

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.http.ApiException
import com.zs.base_library.common.toast
import kotlinx.coroutines.*
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


/**
 * typealias用于省略如泛型之类用不到的信息
 */
typealias Error = suspend (e: ApiException) -> Unit

/**
 * des 基础数据层
 * @date 2020/5/18
 * @author zs
 *
 * @param coroutineScope 注入viewModel的coroutineScope用于协程管理
 * @param errorLiveData 业务出错或者爆发异常，由errorLiveData通知视图层去处理
 */
open class BaseRepository(
    private val coroutineScope: CoroutineScope,
    private val errorLiveData: MutableLiveData<ApiException>
) {

    /**
     * 对协程进行封装,统一处理错误信息
     *
     * @param block   执行中
     * @param success 执行成功
     */
    protected fun <T> launch(
        block: suspend () -> T
        , success: suspend (T) -> Unit
        , error:Error? = null): Job {
        return coroutineScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    block()
                }
            }.onSuccess {
                success(it)
            }.onFailure {
                it.printStackTrace()
                getApiException(it).apply {
                    error?.invoke(this)
                    toast(errorMessage)
                    //统一响应错误信息
                    errorLiveData.value = this
                }
            }
        }
    }

    /**
     * 捕获异常信息
     */
    private fun getApiException(e: Throwable): ApiException {
        return when (e) {
            is UnknownHostException -> {
                ApiException("网络异常", -1)
            }
            is JSONException -> {//|| e is JsonParseException
                ApiException("数据异常", -1)
            }
            is SocketTimeoutException -> {
                ApiException("连接超时", -1)
            }
            is ConnectException -> {
                ApiException("连接错误", -1)
            }
            is HttpException -> {
                ApiException("http code ${e.code()}", -1)
            }
            is ApiException -> {
                e
            }
            /**
             * 如果协程还在运行，个别机型退出当前界面时，viewModel会通过抛出CancellationException，
             * 强行结束协程，与java中InterruptException类似，所以不必理会,只需将toast隐藏即可
             */
            is CancellationException -> {
                ApiException("", -10)
            }
            else -> {
                ApiException("未知错误", -1)
            }
        }
    }
}