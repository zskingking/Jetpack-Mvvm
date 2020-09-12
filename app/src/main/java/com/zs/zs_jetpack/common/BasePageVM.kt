package com.zs.zs_jetpack.common

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseViewModel
import com.zs.base_library.common.isGpListEmpty

/**
 * @author zs
 * @date 2020/9/12
 */
open class BasePageVM : BaseViewModel() {

    companion object {
        /**
         * 空数据
         */
        const val TYPE_EMPTY = 100

        /**
         * 没有数据了
         */
        const val TYPE_NO_DATA = 200
    }

    /**
     * 统一处理列表刷新
     * @param contentList 加载到的列表
     * @param liveData 当前存储列表的liveData
     * @param resultLiveData 加载结果
     */
    protected fun <T> handleRefresh(
        contentList: MutableList<T>?,
        liveData: MutableLiveData<MutableList<T>>,
        resultLiveData: MutableLiveData<Int>
    ): MutableList<T> {
        liveData.value.apply {
            val list = mutableListOf<T>()
            contentList?.let {
                list.addAll(it)
            }
            //第一页并且数据为空
            if (isGpListEmpty(list)) {
                resultLiveData.postValue(TYPE_EMPTY)
            }
            //当前页长度不满一页，显示foot提示
            if (list.size == 0) {
                resultLiveData.postValue(TYPE_NO_DATA)
            }
            return list
        }
    }

    /**
     * 统一处理列表加载更多
     * @param contentList 加载到的列表
     * @param liveData 当前存储列表的liveData
     * @param resultLiveData 加载结果
     */
    protected fun <T> handleLoadMore(
        contentList: MutableList<T>?,
        liveData: MutableLiveData<MutableList<T>>,
        resultLiveData: MutableLiveData<Int>
    ): MutableList<T> {
        liveData.value.apply {
            val list = this ?: mutableListOf()
            contentList?.let { it1 ->
                list.addAll(it1)
            }
            //当前页长度不满一页，显示foot提示。
            //此处判断逻辑应该为contentList?.size<pageSize,但由于api没有给pageSize请求参数，所以将就着这样写
            if (contentList?.size == 0) {
                resultLiveData.postValue(TYPE_NO_DATA)
            }
            return list
        }
    }

}