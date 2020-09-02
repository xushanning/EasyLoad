package com.xu.easyload.ext

import android.app.Activity
import android.view.View
import com.xu.easyload.EasyLoad
import com.xu.easyload.service.ILoadService

/**
 * @author 言吾許
 * 扩展，让使用更简便
 */

/**
 * 注入到activity中去
 */
fun inject(activity: Activity, func: EasyLoad.LocalBuilder.() -> Unit): ILoadService {
    return EasyLoad.initLocal().inject(activity) {
        this.func()
    }
}

/**
 * 注入到activity中去
 */
fun inject(activity: Activity): ILoadService {
    return EasyLoad.initLocal().inject(activity)
}

/**
 * 注入到view中去
 */
fun inject(view: View, func: EasyLoad.LocalBuilder.() -> Unit): ILoadService {
    return EasyLoad.initLocal().inject(view) {
        this.func()
    }
}

/**
 * 注入到view中去
 */
fun inject(view: View): ILoadService {
    return EasyLoad.initLocal().inject(view)
}

/**
 * 全局初始化EasyLoad
 */
fun initEasyLoad(func: EasyLoad.GlobalBuilder.() -> Unit) {
    EasyLoad.initGlobal().init {
        this.func()
    }
}
