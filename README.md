# FunN
（因为 数据来源，接口，app都是本人独立完成，所以可能更新会很慢，请见谅）<br>
<br>
描述：这是一款基于MVP模式开发的,目的是给用户带来开心，其中有
        <item>段子手</item>,
        <item>搞笑</item>,
        <item>清纯妹子</item>,
        <item>性感美女</item>,
        <item>动态图</item>,
        <item>邪恶图片</item>
几个模块，包括了文字笑话，搞笑图片，美女图片，搞笑动态图.<br>
<br>
##### 功能
只是提供了文字小号的分享，图片和gif的等浏览和下载功能。
<br>
####  准备新增功能
* 用户管理（支持第三方登陆）.<br>
* 支持图片和gif分享到第三方软件（sharesdk）<br>
* 添加搞笑视频模块<br>
* 用户发表功能<br>
* 用户评论功能<br>
<br>
##### 所以说还有很长的路要走
<br>
## 预览
<p>
<img src="screenshot/splash.jpg" width="32%" />
<img src="screenshot/dzs.jpg" width="32%" />
<img src="screenshot/gaoxiao.jpg" width="32%" />

<img src="screenshot/mm.jpg" width="32%" />
<img src="screenshot/dongtai.jpg" width="32%" />
<img src="screenshot/mm_detail.jpg" width="32%" />

<img src="screenshot/loading.jpg" width="32%" />
<img src="screenshot/user_info.jpg" width="32%" />
</p>

## 实现架构

### 数据层面

应用除了对少数内容进行缓存，其他内容均直接从网络获取。

- 使用 Volley 及部分自定义增强处理网络请求。
- 使用 Gson 自动填充数据模型。
- 使用 ImageLoader 加载图片。
- 使用 fresco 和android-async-http 加载gif.
- 使用 EventBus 同步不同页面间对象状态。

### 界面层面

使用 Support Library 中的 AppCompat、Design、CardView、RecyclerView 进行 Material Design 实现，在必要时引入/自己写作第三方库以实现部分界面元素和效果。

界面实现一般分为 Activity、Fragment、Adapter 三个模块，分别负责作为容器，发起请求、展示数据和用户交互，以及数据/交互绑定。


## 第三方库
- [PhotoView](https://github.com/chrisbanes/PhotoView)
- [Gson](https://github.com/google/gson)
- [ButterKnife](https://github.com/JakeWharton/butterknife)
- [Volley](https://github.com/mcxiaoke/android-volley)
- [EventBus](https://github.com/greenrobot/EventBus)
- [SwipeRefreshLayout](https://github.com/hanks-zyh/SwipeRefreshLayout)
- [PagerSlidingTab](https://github.com/astuetz/PagerSlidingTabStrip)
- [CircularImageView](https://github.com/lopspower/CircularImageView)
- [android-gif-drawable](https://github.com/koral--/android-gif-drawable/)
- [RippleEffect](https://github.com/traex/RippleEffect)
- [RippleEffect](https://github.com/chrisbanes/PhotoView)
- [materialiconlib]
- [SuperToasts ](https://github.com/JohnPersano/SuperToasts)
- [material-ripple](https://github.com/balysv/material-ripple)
- [Material](https://github.com/rey5137/material)
- 等


## 数据来源
应该有很多人都关心数据来源吧！这里我要说下：<br>









