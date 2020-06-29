package com.zs.zs_jetpack.ui.main.mine


import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.base.LazyVmFragment
import com.zs.base_library.common.setNoRepeatClick
import com.zs.base_library.common.toast
import com.zs.zs_jetpack.R
import kotlinx.android.synthetic.main.fragment_mine.*


/**
 * des 我的
 * @author zs
 * @date 2020-05-14
 */
class MineFragment : LazyVmFragment() {

    override fun lazyInit() {

    }


    override fun getLayoutId(): Int? {
        return R.layout.fragment_mine
    }

    override fun getDataBindingConfig(): DataBindingConfig? {
        return null
    }

    override fun onClick() {
        setNoRepeatClick(
            ivHead, tvName, tvId, llHistory, llRanking
            , clIntegral, clCollect, clArticle, clWebsite, clGirl, clSet
        ) {
            when (it.id) {
                R.id.ivHead -> toast("我只是一只睡着的小老鼠...")
                R.id.tvName -> toast("请先登录～")
                R.id.tvId -> {

                }
                R.id.llHistory -> nav().navigate(R.id.action_main_fragment_to_history_fragment)

                R.id.llRanking -> {
                    nav().navigate(R.id.action_main_fragment_to_play_fragment)
                }
                R.id.clIntegral -> {

                }
                R.id.clCollect -> {

                }
                R.id.clArticle -> {

                }
                R.id.clWebsite -> {

                }
                R.id.clGirl -> {

                }
                R.id.clSet -> {

                }

            }
        }
    }

}
