package com.zs.zs_jetpack.http

class BaseResponse<T> {
    var data: T? = null
    var errorMsg = ""
    var errorCode = 0
}