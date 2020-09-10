package com.zs.zs_jetpack.ui.integral

/**
 * des 获取积分记录
 * @author zs
 * @date 2020-03-17
 */
class IntegralListBean {
    /**
     * 积分计数
     */
    var integralCount = ""

    /**
     * 时间
     */
    var time = ""

    /**
     * 积分描述
     */
    var des = ""

    companion object {
        /**
         * 将后端数据转换为本地定义的数据结构,原因有三
         *
         * 1.将适配器数据和后端隔离,避免后端调整数据牵连到适配器,本地定义的数据和适配器只与设计图保持一致
         * 2.很多情况下后端返回的数据需要我们要二次处理,要么在UI层处理，要么在数据层处理，我个人认为在数据层处理比较合适，
         *   UI层拿到数据无需处理直接渲染。但是这种情况下，数据层要组装字段必须得创建新的字段，避免混淆所以直接独立出一个类
         * 3.做diff运算时更容易操作
         */
        fun trans(list: MutableList<IntegralRecordBean.DatasBean>): MutableList<IntegralListBean> {
            return list.map { bean ->
                IntegralListBean().apply {
                    val desc = bean.desc
                    val firstSpace = desc?.indexOf(" ")
                    val secondSpace = firstSpace?.plus(1)?.let { desc.indexOf(" ", it) }

                    integralCount = "+${bean.coinCount}"
                    time = secondSpace?.let { desc.substring(0, it) } ?: ""
                    des = secondSpace?.plus(1)?.let {
                        desc.substring(it)
                            .replace(",", "")
                            .replace("：", "")
                            .replace(" ", "")
                    } ?: ""
                }
            }.toMutableList()

        }
    }
}
