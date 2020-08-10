EasyLoad
==
|作者|言吾|
|---|---
|简书|简书

****

`EasyLoad`是一个基于Koltin、专注于AndroidX简单易用的页面状态加载框架，支持Activity、Fragment和View，
对布局文件实现零侵入。


* :heavy_check_mark:支持Activity、Fragment和View
* :heavy_check_mark:支持全局状态(全局生效)和局部状态(只在当前页面生效，其他页面调用会报错)
* :heavy_check_mark:对布局文件零入侵
* :heavy_check_mark:支持子线程切换状态
* :heavy_check_mark:支持错误页面点击重新加载监听
* :heavy_check_mark:支持状态改变监听
* :heavy_check_mark:支持`ConstraintLayout`和`SmartRefreshLayout`
* :heavy_check_mark:完全自定义状态页面(继承`BaseState`类)

## 使用EasyLoad


#### 添加依赖
```
compile 'xxxxx'
```

#### 一、全局配置

全局配置只能初始化一次，多次配置会报错。

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
#### 二、自定义状态类
```kotlin
class EmptyState : BaseState() {
    /**
     * 设置布局
     */
    override fun onCreateView(): Int {
        return R.layout.view_easy_load_empty
    }
    /**
    *是否允许重新加载 
    */
    override fun canReloadable(): Boolean {
        return true
    }

}
```
#### 三、页面配置
注入到`Activity`中
```kotlin
  val service = EasyLoad.initLocal()
                .inject(this)
```
完整用法

```kotlin
        val service = EasyLoad.initLocal()
                //添加LocalState，只在本target中生效，其他target调用会报错
                .addLocalState(PlaceHolderState())
                //是否展示默认
                .showDefault(true)
                //LocalDefault会覆盖GlobalDefault
                .setLocalDefaultState(PlaceHolderState::class.java)
                //设置重新加载监听方式①
                //.setOnReloadListener { iLoadService, clickState, view ->
                //do something
                //}
                .inject(this) {
                    //设置重新加载监听方式②
                    setOnReloadListener { iLoadService, clickState, view ->
                        when (clickState) {
                            is PlaceHolderState ->
                                Log.d("TAG","PlaceHolderState")
                            is ErrorState ->
                                Log.d("TAG","ErrorState")
                            is SuccessState ->
                                Log.d("TAG","SuccessState")
                        }

                        //可以在子线程中使用
                        Thread(Runnable {
                            iLoadService.showState(PlaceHolderState::class.java)
                            SystemClock.sleep(4000)
                            iLoadService.showState(SuccessState::class.java)
                        }).start()
                    }
                    //设置状态变更监听
                    setOnStateChangeListener { view, currentState ->
                        when (currentState) {
                            is PlaceHolderState ->
                                Log.d(tag, "PlaceHolderState")
                            is ErrorState ->
                                Log.d(tag, "ErrorState")
                            is SuccessState ->
                                Log.d(tag, "SuccessState")
                        }
                    }
                }
```

注入到`Fragment`中

```kotlin
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_a, container, false)
        service = EasyLoad.initLocal()
                .addLocalState(PlaceHolderState())
                .inject(view) {
                    setOnReloadListener { iLoadService, clickState, view ->
                        iLoadService.showState(PlaceHolderState::class.java)
                        Handler().postDelayed({
                            iLoadService.showState(SuccessState::class.java)
                        }, 3000)
                    }
                }
        return service.getParentView()
    }
```
注入到`View`中

```kotlin
        val sView = EasyLoad.initLocal()
                .addLocalState(LoadingState2())
                .setLocalDefaultState(LoadingState2::class.java)
                //ConstraintLayout 2.0版本以上，会出现不显示的问题，可以通过设置specialSupport为true来支持,会损失性能
                //.specialSupport(true)
                .inject(cl_child)
```


#### License
****
```
Copyright 2017, YanWu

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

