package com.zs.zs_jetpack.ui.publish

import android.os.Bundle
import androidx.lifecycle.Observer
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.common.setNoRepeatClick
import com.zs.base_library.common.toast
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.R
import kotlinx.android.synthetic.main.fragment_publish.*

/**
 * des 发布文章
 * @author zs
 * @data 2020/7/12
 */
class PublishFragment : BaseVmFragment() {

    private lateinit var publishVM: PublishVM

    override fun initViewModel() {
        publishVM = getFragmentViewModel(PublishVM::class.java)
    }

    override fun observe() {
        publishVM.publishLiveData.observe(this, Observer {
            //结束当前界面
            nav().navigateUp()
            toast("发布成功")
        })
    }

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun onClick() {
        setNoRepeatClick(ivBack, btPublish) {
            when (it.id) {
                R.id.ivBack -> nav().navigateUp()
                R.id.btPublish -> publishVM.publish()
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_publish

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_publish, publishVM)
            .addBindingParam(BR.vm, publishVM)
    }
}