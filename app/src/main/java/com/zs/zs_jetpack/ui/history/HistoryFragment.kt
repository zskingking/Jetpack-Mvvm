package com.zs.zs_jetpack.ui.history

import androidx.fragment.app.Fragment
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.base.LazyVmFragment
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.databinding.FragmentHistoryBinding

/**
 * A simple [Fragment] subclass.
 */
class HistoryFragment : LazyVmFragment<FragmentHistoryBinding>() {

    override fun lazyInit() {
    }

    override fun getLayoutId() = R.layout.fragment_history
}
