package com.zs.zs_jetpack.ui.play

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.zs.base_library.common.clickNoRepeat
import com.zs.base_library.common.initFragment
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.ui.play.collect.PlayCollectFragment
import com.zs.zs_jetpack.ui.play.history.PlayHistoryFragment
import com.zs.zs_jetpack.ui.play.local.PlayLocalFragment
import kotlinx.android.synthetic.main.fragment_play_list.*

/**
 * des
 * @author zs
 * @date 2020/10/30
 */
class PlayListFragment : DialogFragment() {

    override fun onStart() {
        super.onStart()
        dialog?.setCanceledOnTouchOutside(true)
        //设置fragment高度 、宽度
        val dialogHeight = requireContext().resources.displayMetrics.heightPixels
        val dialogWidth = requireContext().resources.displayMetrics.widthPixels
        dialog?.window?.setLayout(dialogWidth, dialogHeight)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_play_list, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vpPlayList.initFragment(childFragmentManager, mutableListOf<Fragment>().apply {
            add(PlayLocalFragment())
            add(PlayHistoryFragment())
            add(PlayCollectFragment())
        })
        vpPlayList.offscreenPageLimit = 3
        root.clickNoRepeat {
            dismissAllowingStateLoss()
        }
    }

    /**
     * 主题
     */
    override fun getTheme(): Int {
        return com.zs.base_library.R.style.sheet_dialog_style
    }

}