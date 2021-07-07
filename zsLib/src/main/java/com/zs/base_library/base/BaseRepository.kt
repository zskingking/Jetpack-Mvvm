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
 * 错误方法
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
open class BaseRepository {
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var errorLiveData: MutableLiveData<ApiException>

    @Deprecated("后期会被剔除")
    constructor(
        coroutineScope: CoroutineScope,
        errorLiveData: MutableLiveData<ApiException>
    ) {
        this.coroutineScope = coroutineScope
        this.errorLiveData = errorLiveData
    }
    constructor()

    /**
     * 对协程进行封装,统一处理错误信息
     *
     * @param block   执行中
     * @param success 执行成功
     */
    @Deprecated("关于协程的封装有问题，弃用")
    protected fun <T> launch(
        block: suspend () -> T
        , success: suspend (T) -> Unit
        , error: Error? = null
    ): Job {
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
     * 在协程作用域中切换至IO线程
     */
    protected suspend fun <T> withIO(block: suspend () -> T): T {
        return withContext(Dispatchers.IO) {
            block.invoke()
        }
    }

    /**
     * 捕获异常信息
     */
    @Deprecated("后期会被剔除")
    private fun getApiException(e: Throwable): ApiException {
        return when (e) {
            is UnknownHostException -> {
                ApiException("网络异常", -100)
            }
            is JSONException -> {//|| e is JsonParseException
                ApiException("数据异常", -100)
            }
            is SocketTimeoutException -> {
                ApiException("连接超时", -100)
            }
            is ConnectException -> {
                ApiException("连接错误", -100)
            }
            is HttpException -> {
                ApiException("http code ${e.code()}", -100)
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
                ApiException("未知错误", -100)
            }
        }
    }
}