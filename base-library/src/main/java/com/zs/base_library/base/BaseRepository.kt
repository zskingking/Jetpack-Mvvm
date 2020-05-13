package com.zs.base_library.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * des 基础数据层,通过ViewModel注入CoroutineScope对协程生命周期自动管控
 * @date 2020/5/13
 * @author zs
 */
class BaseRepository(private val coroutineScope: CoroutineScope) {

    protected fun lanunch(){
        coroutineScope.launch {
            //执行线程任务
            runCatching{

            }
            //执行成功
            .onSuccess {

            }
            //执行出错
            .onFailure {

            }
        }
    }


}