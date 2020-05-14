package com.zs.base_library.http

import java.lang.reflect.ParameterizedType

/**
 * des 服务端封装类
 * @author zs
 * @date 2020-05-14
 */
class ApiResponse<T>(private var data: T,
                     private val errorCode: Int,
                     private val errorMsg: String) {
    /**
     * 对数据脱壳
     * 统一处理服务端业务code以及错误信息
     */
    fun getData(): T {
        //业务请求成功
        return if (errorCode == 0) {
            //当业务执行成功服务端可能会返回null(如收藏),
            //避免业务层做null判断,通过反射将null替换为T类型空对象
            if (data == null) {
                val tClass = (javaClass.genericSuperclass
                        as ParameterizedType).actualTypeArguments[0] as Class<T>
                data = tClass.newInstance()
            }
            data
        }
        //业务执行失败抛出异常
        else {
            throw ApiException(errorMsg,errorCode)
        }
    }
}