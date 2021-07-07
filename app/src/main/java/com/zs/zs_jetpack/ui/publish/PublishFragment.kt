package com.zs.zs_jetpack.ui.publish

import android.os.Bundle
import androidx.lifecycle.Observer
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.common.clickNoRepeat
import com.zs.base_library.common.setNoRepeatClick
import com.zs.base_library.common.toast
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.databinding.FragmentPublishBinding
import com.zs.zs_jetpack.view.DialogUtils

/**
 * des 发布文章
 * @author zs
 * @data 2020/7/12
 */
class PublishFragment : BaseVmFragment<FragmentPublishBinding>() {

    private lateinit var publishVM: PublishVM

    override fun initViewModel() {
        publishVM = getFragmentViewModel(PublishVM::class.java)
    }

    override fun observe() {
        publishVM.publishLiveData.observe(this, Observer {
            DialogUtils.dismiss()
            //结束当前界面
            nav().navigateUp()
            toast("发布成功")
        })
        publishVM.errorLiveData.observe(this, Observer {
            DialogUtils.dismiss()
        })
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = publishVM
    }

    override fun onClick() {
        binding.ivBack.clickNoRepeat {
            nav().navigateUp()
        }
        binding.btPublish.clickNoRepeat {
            DialogUtils.showLoading(mActivity,"正在发布～")
            publishVM.publish()
        }
    }

    override fun getLayoutId() = R.layout.fragment_publish

}