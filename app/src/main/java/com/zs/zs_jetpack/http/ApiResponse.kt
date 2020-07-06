package com.zs.zs_jetpack.http

import com.zs.base_library.http.ApiException
import java.io.Serializable
import java.lang.reflect.ParameterizedType

/**
 * des Api描述类，用于承载业务信息以及基础业务逻辑判断
 * @date 2020/7/5
 * @author zs
 */
class ApiResponse<T : Any>:Serializable {
    var data: T? = null

    /**
     * 业务信息
     */
    var errorMsg = ""

    /**
     * 业务code
     */
    var errorCode = 0

    fun data(): T {
        when (errorCode) {
            //请求成功
            0, 200 -> {
                //避免业务层做null判断,通过反射将null替换为T类型空对象
                if (data == null) {
                    val tClass = (javaClass.genericSuperclass
                            as ParameterizedType).actualTypeArguments[0] as Class<T>
                    data = tClass.newInstance()
                }
                return data!!
            }
            //登录失败
            -1001 -> {
            }
        }
        //其他错误
        throw ApiException(errorMsg, errorCode)
    }

}