package com.zs.zs_jetpack.ui.main.square.system

import android.os.Bundle
import androidx.lifecycle.Observer
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.base.LazyVmFragment
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.constants.Constants
import kotlinx.android.synthetic.main.fragment_system.*

/**
 * des 体系
 * @date 2020/7/10
 * @author zs
 */
class SystemFragment : LazyVmFragment() {

    private lateinit var systemVM: SystemVM
    private var adapter: SystemAdapter? = null
    override fun initViewModel() {
        systemVM = getFragmentViewModel(SystemVM::class.java)
    }

    override fun observe() {
        systemVM.systemLiveData.observe(this, Observer {
            adapter?.setNewData(it)
        })
    }

    override fun lazyInit() {
        initView()
        loadData()
    }

    override fun initView() {
        adapter = SystemAdapter { i, j ->
            nav().navigate(R.id.action_main_fragment_to_system_list_fragment,
                Bundle().apply {
                    putInt(Constants.SYSTEM_ID, adapter!!.data[i].children[j].id)
                    putString(Constants.SYSTEM_TITLE, adapter!!.data[i].children[j].name)
                })

        }
        rvSystem.adapter = adapter
    }

    override fun loadData() {
        systemVM.getSystemList()
    }

    override fun getLayoutId() = R.layout.fragment_system

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_system, systemVM)
            .addBindingParam(BR.vm, systemVM)
    }
}