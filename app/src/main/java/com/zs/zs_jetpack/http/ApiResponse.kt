package com.zs.zs_jetpack.http

class ApiResponse<T> {
    var data: T? = null
    var errorMsg = ""
    var errorCode = 0
}