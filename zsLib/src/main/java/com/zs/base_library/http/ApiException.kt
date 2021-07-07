package com.zs.base_library.http

/**
 * 用来封装业务错误信息
 *
 * @author zs
 * @date 2020-05-09
 */
class ApiException(val errorMessage: String, val errorCode: Int) :
    Throwable()