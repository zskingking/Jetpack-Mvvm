package com.zs.zs_jetpack.http

import android.util.Log
import com.zs.base_library.http.ApiException
import java.io.Serializable
import java.lang.reflect.ParameterizedType

/**
 * des Api描述类，用于承载业务信息以及基础业务逻辑判断
 * @date 2020/7/5
 * @author zs
 */
class ApiResponse<T> : Serializable {

    private var data: T? = null

    /**
     * 业务信息
     */
    private var errorMsg = ""

    /**
     * 业务code
     */
    private var errorCode = 0

    fun data(): T {
        Log.i("ApiResponse", "1---data-$data")
        when (errorCode) {
            //请求成功
            0, 200 -> {
                //避免业务层做null判断,通过反射将null替换为T类型空对象
                if (data == null) {
                    Log.i("ApiResponse", "2---data-$data")
                    val tClass =
                        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<ApiResponse<T>>
                    data = tClass.newInstance().data
                }
                return data!!
            }
            //登录失败
            -1 -> {
                throw ApiException("登陆失败", -1)
            }
        }
        //其他错误
        throw ApiException(errorMsg, errorCode)
    }


    fun data(clazz:Class<T>): T {
        Log.i("ApiResponse", "1---data-$data")
        when (errorCode) {
            //请求成功
            0, 200 -> {
                //避免业务层做null判断,通过反射将null替换为T类型空对象
                if (data == null) {
                    data = clazz.newInstance()
                }
                return data!!
            }
            //登录失败
            -1 -> {
                throw ApiException("登陆失败", -1)
            }
        }
        //其他错误
        throw ApiException(errorMsg, errorCode)
    }

}