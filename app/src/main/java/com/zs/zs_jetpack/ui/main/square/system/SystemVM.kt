package com.zs.zs_jetpack.ui.main.square.system

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zs.base_library.base.BaseViewModel
import com.zs.zs_jetpack.bean.ArticleBean

/**
 * des 体系/体系列表
 * @date 2020/7/10
 * @author zs
 */
class SystemVM :BaseViewModel(){

    private val repo by lazy { SystemRepo(viewModelScope,errorLiveData) }
    /**
     * 体系列表数据
     */
    val systemLiveData = MutableLiveData<MutableList<SystemBean>>()
    /**
     * 体系列表数据
     */
    val articleLiveData = MutableLiveData<MutableList<ArticleBean.DatasBean>>()

    /**
     * 收藏
     */
    val collectLiveData = MutableLiveData<Int>()

    /**
     * 取消收藏
     */
    val unCollectLiveData = MutableLiveData<Int>()

    /**
     * 获取体系列表
     */
    fun getSystemList(){
        repo.getSystemList(systemLiveData)
    }

    /**
     * 获取文章列表
     */
    fun getArticleList(isRefresh:Boolean,id:Int){
        repo.getArticleList(isRefresh,id,articleLiveData)
    }

    /**
     * 收藏
     */
    fun collect(id:Int){
        repo.collect(id,collectLiveData)
    }

    /**
     * 取消收藏
     */
    fun unCollect(id:Int){
        repo.unCollect(id,unCollectLiveData)
    }





}