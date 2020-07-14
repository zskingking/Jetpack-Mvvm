package com.zs.zs_jetpack.ui.collect

class CollectBean {


    /**
     * curPage : 1
     * datas : [{"author":"xiaoyang","chapterId":440,"chapterName":"官方","courseId":13,"desc":"
     *
     *很久以前有Activity.onResume就是界面可见的说法，这种说法毫无疑问是不准确的。<\/p>\r\n
     *
     *问题是：<\/p>\r\n\r\n 1. Activity.onCreate 和 Activity.onResume，在调用时间上有差别么？可以从Message调度去考虑。<\/li>\r\n 1. 有没有一个合理的时机，让我们认为Activity 界面可见了？<\/li>\r\n<\/ol>","envelopePic":"","id":120115,"link":"https://wanandroid.com/wenda/show/12175","niceDate":"2小时前","origin":"","originId":12175,"publishTime":1583732093000,"title":"每日一问 | 很久以前有Activity.onResume就是界面可见的说法，这种说法错了多少？","userId":36628,"visible":0,"zan":0},{"author":"","chapterId":249,"chapterName":"干货资源","courseId":13,"desc":"","envelopePic":"","id":119996,"link":"https://wanandroid.com/blog/show/2701","niceDate":"7小时前","origin":"","originId":10916,"publishTime":1583713076000,"title":"玩 Android 交流星球 限时开放","userId":36628,"visible":0,"zan":0},{"author":"xiaoyang","chapterId":440,"chapterName":"官方","courseId":13,"desc":"
     *
     *之前我们讨论过 [View的onAttachedToWindow ,onDetachedFromWindow 调用时机<\/a> 。<\/p>\r\n](\"https://wanandroid.com/wenda/show/8488\")
     *
     *这个机制在RecyclerView卡片中还适用吗？<\/p>\r\n
     *
     *例如我们在RecyclerView的Item的onBindViewHolder时，利用一个CountDownTimer去做一个倒计时显示 / 或者是有一个属性动画效果？<\/p>\r\n\r\n 1. 到底在什么时候可以cancel掉这个倒计时/ 动画，而不影响功能了（滑动到用户可见范围内，倒计时/动画 运作正常）?<\/li>\r\n 1. 有什么方法可以和onBindViewHolder 对应吗？就像onAttachedToWindow ,onDetachedFromWindow这样 。<\/li>\r\n<\/ol>","envelopePic":"","id":119994,"link":"https://wanandroid.com/wenda/show/12148","niceDate":"7小时前","origin":"","originId":12148,"publishTime":1583713073000,"title":"每日一问 RecyclerView卡片中持有的资源，到底该什么时候释放？","userId":36628,"visible":0,"zan":0},{"author":"","chapterId":502,"chapterName":"自助","courseId":13,"desc":"","envelopePic":"","id":119502,"link":"https://juejin.im/post/5e60ecd4e51d4526ed66bdcc","niceDate":"2020-03-06 13:52","origin":"","originId":12202,"publishTime":1583473970000,"title":"LiveData详细分析","userId":36628,"visible":0,"zan":0},{"author":"","chapterId":78,"chapterName":"性能优化","courseId":13,"desc":"","envelopePic":"","id":102230,"link":"https://www.jianshu.com/p/28b9ee94d515","niceDate":"2019-11-26 21:04","origin":"","originId":10479,"publishTime":1574773455000,"title":"Android应用测速组件实现原理","userId":36628,"visible":0,"zan":0}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 5
     */

    var curPage: Int = 0
    var offset: Int = 0
    var over: Boolean = false
    var pageCount: Int = 0
    var size: Int = 0
    var total: Int = 0
    var datas: MutableList<DatasBean>? = null

    class DatasBean {
        /**
         * author : xiaoyang
         * chapterId : 440
         * chapterName : 官方
         * courseId : 13
         * desc :
         *
         *很久以前有Activity.onResume就是界面可见的说法，这种说法毫无疑问是不准确的。
         *
         * 问题是：
         *
         *  1. Activity.onCreate 和 Activity.onResume，在调用时间上有差别么？可以从Message调度去考虑。
         *  1. 有没有一个合理的时机，让我们认为Activity 界面可见了？
         *
         * envelopePic :
         * id : 120115
         * link : https://wanandroid.com/wenda/show/12175
         * niceDate : 2小时前
         * origin :
         * originId : 12175
         * publishTime : 1583732093000
         * title : 每日一问 | 很久以前有Activity.onResume就是界面可见的说法，这种说法错了多少？
         * userId : 36628
         * visible : 0
         * zan : 0
         */

        var author: String? = null
        var chapterId: Int = 0
        var chapterName: String? = null
        var courseId: Int = 0
        var desc: String? = null
        var envelopePic: String? = null
        var id: Int = 0
        var link: String? = null
        var niceDate: String? = null
        var origin: String? = null
        var originId: Int = 0
        var publishTime: Long = 0
        var title: String? = null
        var userId: Int = 0
        var visible: Int = 0
        var zan: Int = 0
    }
}
