package com.zs.zs_jetpack.ui.my

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseViewModel
import com.zs.base_wa_lib.article.ArticleBean

/**
 * @date 2020/7/13
 * @author zs
 */
class MyArticleVM :BaseViewModel(){

    private val repo by lazy { MyArticleRepo() }

    val myLiveDate = MutableLiveData<MutableList<ArticleBean.DatasBean>>()

    val deleteLiveData = MutableLiveData<Int>()

    fun getMyArticle(){
        launch {
            myLiveDate.value = repo.getMyArticle()?.toMutableList()
        }
    }

    fun loadMore(){
        launch {
            val list = myLiveDate.value
            repo.loadMore()?.toMutableList()?.let {
                list?.addAll(it)
            }
            myLiveDate.value = list
            handleList(myLiveDate)
        }
    }

    fun delete(id:Int){
        launch {
            repo.delete(id)
            deleteLiveData.value = id
        }
    }
}