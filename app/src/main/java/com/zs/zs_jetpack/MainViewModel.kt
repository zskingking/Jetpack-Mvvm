package com.zs.zs_jetpack

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseViewModel
import com.zs.zs_jetpack.bean.Article
import com.zs.zs_jetpack.http.RetrofitHelper

/**
 * @date 2020/5/14
 * @author zs
 */
class MainViewModel:BaseViewModel() {

    val testLiveData = MutableLiveData<MutableList<Article>>()

    fun test(){
        launch(
            block = {
                RetrofitHelper.apiService?.getTopArticleList()?.getData()
            },
            success = {
                testLiveData.value = it
            },
            error = {

            }
        )
    }

}