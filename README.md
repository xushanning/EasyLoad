EasyLoad

`EasyLoad`是一个基于Koltin、专注于AndroidX简单易用的页面状态加载框架，支持Activity、Fragment和View，
对布局文件实现零侵入，


* :star:支持Activity、Fragment和View
* :star:支持全局状态(全局生效)和局部状态(只在当前页面生效，其他页面调用会报错)
* :star:对布局文件零入侵
* :star:支持子线程切换状态
* :star:支持错误页面点击重新加载监听
* :star:支持状态改变监听

##使用EasyLoad
####一、全局配置
只支持一次对全局配置的初始化，多次配置会报错。

```kotlin
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        EasyLoad.initGlobal()
                .addGlobalState(ErrorState())//添加错误布局状态
                .addGlobalState(EmptyState())//添加空布局状态
                .addGlobalState(LoadingState())//添加加载布局状态
                .setGlobalDefaultState(LoadingState::class.java)//设置默认全局
    }
}
```