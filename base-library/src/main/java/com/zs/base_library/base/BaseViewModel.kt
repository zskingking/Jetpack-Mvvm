package com.zs.base_library.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * des 基础vm,做协程封装
 * @date 2020/5/13
 * @author zs
 */

typealias Block<T> = suspend () -> T
typealias Error = suspend (e: Exception) -> Unit

class BaseViewModel:ViewModel() {


    protected fun launch(block: Block<Unit>,error: Error):Job{
        return viewModelScope.launch {
            runCatching {
                block.invoke()
            }
        }
    }
}