### 1. 背景


`图片如若不能浏览请到我的博客主页查看:`[https://juejin.im/post/5f09ac336fb9a07e93303162](https://juejin.im/post/5f09ac336fb9a07e93303162)


之前公司项目用的一直是`MVP`框架，我个人也在几个月前基于`鸿神 WanAndroid API`开发了一款`MVP`版的`App`，使用`MVP`的过程最深的感受是开发效率极低，往往写一大堆接口，可复用的屈指可数。年初了解了`Jetpack`模式下的`MVVM`，在`LiveData、ViewModel、DataBinDing`的加持下实现了`单向依赖`和`数据绑定`，代码量大幅度减少，根据`Jetpack`的特性项目稳定性也提升了不少。

为了更深入的理解`Jetpack`中各个组件，在前段时间基于`Jetpack MVVM`又实现了一版` WanAndroid`。相比上一版的`MVP`增加了`夜间模式`和`音乐播放器`，播放器界面仿照`网易云音乐`。`App`中也大量的使用属性动画让界面简约而不简陋。先上图look一波



![](https://user-gold-cdn.xitu.io/2020/7/11/1733e02a11e9437c?w=3072&h=3072&f=jpeg&s=1292783)

`先附上github:`[https://github.com/zskingking/Jetpack-Mvvm](https://github.com/zskingking/Jetpack-Mvvm)

### 2. 应用技术
基础框架选用`MVVM`，选用的`Jetpack`组件包括`Lifecycle、ViewModel、LiveData、DataBinDing、Navigation、Room`。

项目基于`Navigation`由单`Activity`多`Fragment`实现，使用这种模式给我最直观的感受就是`快`，比如点击搜索进入搜索界面的衔接动画，在多`Activity`之间是不可能这么连贯的。

整个项目全部使用`Kotlin`语言，广泛应用了`协程`编写了大量的`扩展函数`。


关于每个模块的职责我是这样定义的：

#### Model
对应项目中`Repository`，做数据请求以及业务逻辑。很多人将业务逻辑编写到`VM`层，但我个人认为写在`Model`层更为合适，因为数据和业务逻辑本身就是息息相关，拿到数据及时处理业务逻辑，最后通过`ViewModel`注入的`LiveData`将数据发送给`View`层。在该层我也对协程做了封装，以及统一捕获处理错误信息。

#### ViewModel
基于`Jetpack`中的`ViewModel`进行封装(友情提示：`Jetpack ViewModel`和`MVVM ViewModel`没有半毛钱关系，切勿将两个概念混淆)。在项目中`VM`层职责很简单，通过内部通过`LiveData`做数据存储，以及结合`DataBinding`做数据绑定。

#### View
尽量只做UI渲染。与`MVP`中不同，View是通过DataBinding与数据进行绑定，`Activity`或`Fragment`非常轻盈只专注于生命周期的管理，数据渲染基本全部由`DataBinding+BindAdapter`实现。

关于`MVVM`模版类的封装可至`package com.zs.base_library.base(包名)`下查看。


#### 网络层
关于网络层继续使用`OkHttp Retrofit`，并对Retrofit多`ApiService`以及多域名进行了封装。配合`Repository`中封装的协程使用美得不能再美。

#### 数据库
项目中`历史记录`是在本地数据库进行维护的，关于数据库使用了`Jetpack`中的`Room`。

#### 主题切换
`Android`原生提供的夜间切换好像又`API`版本限制，所以就没有用。我个人在本地维护了两套主题，可动态切换。当前包含`白天、夜间`两套主题

### 3. 关于注释
去年在我的`Leader`强行督促下养成了写注释的规习惯，我个人对写注释的要求也越来越高。

项目中运用了大量的设计模式，每用到一种`设计模式`我都会结合当时场景进行解释，比如播放器中某个接口，我会这样写注释：
```

/**
 * des 所有的具体Player必须实现该接口,目的是为了让PlayManager不依赖任何
 *     具体的音频播放实现,原因大概有两点
 *
 *     1.PlayManager包含业务信息,Player不应该与业务信息进行耦合,否则每次改动都会对业务造成影响
 *
 *     2.符合开闭原则,如果需要对Player进行替换势必会牵连到PlayManager中的业务,因而造成不必要的麻烦
 *       如果基于IPlayer接口编程,扩展出一个Player即可,正所谓对扩展开放、修改关闭
 *
 * @author zs
 * @data 2020-06-23
 */
interface IPlayer {
    ....
    ....
}

/**
 * des 音频管理
 *     通过单例模式实现,托管音频状态与信息,并且作为唯一的可信源
 *     通过观察者模式统一对状态进行分发
 *     实则是一个代理,将目标对象Player与调用者隔离,并且在内部实现了对观察者的注册与通知
 * @author zs
 * @data 2020/6/25
 */
class PlayerManager private constructor() : IPlayerStatus {
     ....
     ....
}
```

* `关于播放器的设计我觉得还是有些地方值得和大家分享，后面我会单独写一篇文章进行分析。`


### 写在最后
此项目中你很难看到不明不白的代码。`Jetpack`和`Kotlin`是大势所趋，既然拒绝不了那何不开心的拥抱。功能目前已完成`90%`，代码也在持续优化，欢迎大家关注、下载源代码，让我们共同学习、共同进步。

再次附上`github:`[https://github.com/zskingking/Jetpack-Mvvm](https://github.com/zskingking/Jetpack-Mvvm)，如果觉得对你有帮助麻烦给个`star`

