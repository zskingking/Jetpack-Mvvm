package com.zs.zs_jetpack.ui.integral

/**
 * des 获取积分记录
 * @author zs
 * @date 2020-03-17
 */
class IntegralRecordBean {


    /**
     * curPage : 1
     * datas : [{"coinCount":29,"date":1584398959000,"desc":"2020-03-17 06:49:19 签到 , 积分：10 + 19","id":164906,"reason":"签到","type":1,"userId":36628,"userName":"18616720137"},{"coinCount":28,"date":1584318167000,"desc":"2020-03-16 08:22:47 签到 , 积分：10 + 18","id":163201,"reason":"签到","type":1,"userId":36628,"userName":"18616720137"},{"coinCount":27,"date":1584201864000,"desc":"2020-03-15 00:04:24 签到 , 积分：10 + 17","id":162237,"reason":"签到","type":1,"userId":36628,"userName":"18616720137"},{"coinCount":26,"date":1584115251000,"desc":"2020-03-14 00:00:51 签到 , 积分：10 + 16","id":161702,"reason":"签到","type":1,"userId":36628,"userName":"18616720137"},{"coinCount":10,"date":1584062485000,"desc":"2020-03-13 09:21:25 分享文章 , 积分：10 + 0","id":161155,"reason":"分享文章","type":3,"userId":36628,"userName":"18616720137"},{"coinCount":25,"date":1584057594000,"desc":"2020-03-13 07:59:54 签到 , 积分：10 + 15","id":161033,"reason":"签到","type":1,"userId":36628,"userName":"18616720137"},{"coinCount":24,"date":1583974341000,"desc":"2020-03-12 08:52:21 签到 , 积分：10 + 14","id":160328,"reason":"签到","type":1,"userId":36628,"userName":"18616720137"},{"coinCount":23,"date":1583893980000,"desc":"2020-03-11 10:33:00 签到 , 积分：10 + 13","id":159816,"reason":"签到","type":1,"userId":36628,"userName":"18616720137"},{"coinCount":22,"date":1583802746000,"desc":"2020-03-10 09:12:26 签到 , 积分：10 + 12","id":158857,"reason":"签到","type":1,"userId":36628,"userName":"18616720137"},{"coinCount":21,"date":1583712572000,"desc":"2020-03-09 08:09:32 签到 , 积分：10 + 11","id":157349,"reason":"签到","type":1,"userId":36628,"userName":"18616720137"},{"coinCount":20,"date":1583683057000,"desc":"2020-03-08 23:57:37 签到 , 积分：10 + 10","id":157083,"reason":"签到","type":1,"userId":36628,"userName":"18616720137"},{"coinCount":19,"date":1583514889000,"desc":"2020-03-07 01:14:49 签到 , 积分：10 + 9","id":156132,"reason":"签到","type":1,"userId":36628,"userName":"18616720137"},{"coinCount":18,"date":1583473908000,"desc":"2020-03-06 13:51:48 签到 , 积分：10 + 8","id":155841,"reason":"签到","type":1,"userId":36628,"userName":"18616720137"},{"coinCount":17,"date":1575862611000,"desc":"2019-12-09 11:36:51 签到 , 积分：10 + 7","id":110412,"reason":"签到","type":1,"userId":36628,"userName":"18616720137"},{"coinCount":16,"date":1575687514000,"desc":"2019-12-07 10:58:34 签到 , 积分：10 + 6","id":109386,"reason":"签到","type":1,"userId":36628,"userName":"18616720137"},{"coinCount":15,"date":1575535345000,"desc":"2019-12-05 16:42:25 签到 , 积分：10 + 5","id":108294,"reason":"签到","type":1,"userId":36628,"userName":"18616720137"},{"coinCount":14,"date":1575100576000,"desc":"2019-11-30 15:56:16 签到 , 积分：10 + 4","id":104674,"reason":"签到","type":1,"userId":36628,"userName":"18616720137"},{"coinCount":13,"date":1575014903000,"desc":"2019-11-29 16:08:23 签到 , 积分：10 + 3","id":104194,"reason":"签到","type":1,"userId":36628,"userName":"18616720137"},{"coinCount":12,"date":1574901404000,"desc":"2019-11-28 08:36:44 签到 , 积分：10 + 2","id":102980,"reason":"签到","type":1,"userId":36628,"userName":"18616720137"},{"coinCount":11,"date":1574837617000,"desc":"2019-11-27 14:53:37 签到 , 积分：10 + 1","id":102632,"reason":"签到","type":1,"userId":36628,"userName":"18616720137"}]
     * offset : 0
     * over : false
     * pageCount : 2
     * size : 20
     * total : 21
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
         * coinCount : 29
         * date : 1584398959000
         * desc : 2020-03-17 06:49:19 签到 , 积分：10 + 19
         * id : 164906
         * reason : 签到
         * type : 1
         * userId : 36628
         * userName : 18616720137
         */

        var coinCount: Int = 0
        var date: Long = 0
        var desc: String? = null
        var id: Int = 0
        var reason: String? = null
        var type: Int = 0
        var userId: Int = 0
        var userName: String? = null
    }
}
