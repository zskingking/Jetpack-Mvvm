package com.zs.zs_jetpack.ui.history

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zs.base_library.base.LazyVmFragment
import com.zs.zs_jetpack.R
import kotlinx.android.synthetic.main.fragment_history.*

/**
 * A simple [Fragment] subclass.
 */
class HistoryFragment : LazyVmFragment() {


    override fun lazyInit() {
        rvOuter.layoutManager = LinearLayoutManager(context)
        val outerAdapter = OuterAdapter()
        rvOuter.adapter = outerAdapter
        outerAdapter.setNewData(getList())
    }

    override fun getLayoutId(): Int? {
        return R.layout.fragment_history
    }


    private fun getList():MutableList<OuterBean>{
        val list = mutableListOf<OuterBean>()
        for (index in 4..100){
            val imgList = mutableListOf<String>()
            when {
                index%3==1 -> {
                    imgList.add("https://prd-cycontent.oss-cn-hangzhou.aliyuncs.com/dir-content-image/1/cedfc749cc7d4878a5d58fc55a73af1e.jpg")
                }
                index%3==2 -> {
                    imgList.add("https://cyclient-test.oss-cn-shanghai.aliyuncs.com/dir-client-logo-image/4/efc97971e6144e6cb453fdd30aef63c3.jpg")
                    imgList.add("https://prd-cycontent.oss-cn-hangzhou.aliyuncs.com/dir-content-image/14/02ae7fadd089425e8ae0b341a396e68b.jpg")
                }
                else -> {

                    imgList.add("https://cyclient-test.oss-cn-shanghai.aliyuncs.com/dir-client-logo-image/4/efc97971e6144e6cb453fdd30aef63c3.jpg")
                    imgList.add("https://prd-cycontent.oss-cn-hangzhou.aliyuncs.com/dir-content-image/14/02ae7fadd089425e8ae0b341a396e68b.jpg")
                    imgList.add("https://prd-cycontent.oss-cn-hangzhou.aliyuncs.com/dir-content-image/1/cedfc749cc7d4878a5d58fc55a73af1e.jpg")
                }
            }
            val bean = OuterBean()
            bean.title = "$index"
            bean.imgList = imgList
            list.add(bean)
        }
        return list
    }

}
