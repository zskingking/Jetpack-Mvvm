package com.zs.zs_jetpack.http
import com.google.gson.JsonParseException
import com.zs.base_library.http.ApiException
import io.reactivex.Observer
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.lang.reflect.ParameterizedType


/**
 * des 给Response脱壳,对服务器错误统一处理
 *
 * @author zs
 * @date 2020-05-09
 */
abstract class HttpDefaultObserver<T> : Observer<BaseResponse<T>> {

    override fun onComplete() {
    }


    override fun onNext(t: BaseResponse<T>) {
        if (t.errorCode==0) {
            if (t.data==null){
                val tClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
                t.data = tClass.newInstance()
            }
            t.data?.let { onSuccess(it) }
        }
        //code!=0代表业务出错，进行过滤
        else{
            filterCode(t.errorMsg,t.errorCode)
        }
    }

    override fun onError(e: Throwable) {
        val errorMsg = if (e is UnknownHostException) {
            "网络异常"
        } else if (e is JSONException || e is JsonParseException) {
            "数据异常"
        } else if (e is SocketTimeoutException) {
            "连接超时"
        } else if (e is ConnectException) {
            "连接错误"
        } else if (e is ApiException){
            e.businessMessage
        } else{
            "未知错误"
        }
        onError(errorMsg)
    }

    private fun filterCode(msg: String, code: Int) {
        when (code) {
            //登录失败
            -1001 -> {
                //AppManager.resetUser()
                onError(ApiException(msg, code))
            }
            //未知code,将errorMsg封装成异常,由onError()处理
            else -> onError(ApiException(msg, code))
        }
    }

    abstract fun onSuccess(t:T)
    abstract fun onError(errorMsg:String)

}