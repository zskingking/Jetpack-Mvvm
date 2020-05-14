package com.zs.base_library.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.zs.base_library.BaseApp

/**
 * des Toast工具类
 * @date 2020/5/14
 * @author zs
 */

fun Context.toast(content: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, content, duration).apply {
        show()
    }
}

fun Context.toast(@StringRes id: Int, duration: Int = Toast.LENGTH_SHORT) {
    toast(getString(id), duration)
}


fun toast(content: String, duration: Int = Toast.LENGTH_SHORT) {
    BaseApp.getContext().toast(content, duration)
}

fun toast(@StringRes id: Int, duration: Int= Toast.LENGTH_SHORT) {
    BaseApp.getContext().toast(id, duration)
}

fun longToast(content: String,duration: Int= Toast.LENGTH_LONG) {
    BaseApp.getContext().toast(content,duration)
}

fun longToast(@StringRes id: Int,duration: Int= Toast.LENGTH_LONG) {
    BaseApp.getContext().toast(id,duration)
}


