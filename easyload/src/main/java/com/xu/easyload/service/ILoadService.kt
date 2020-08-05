package com.xu.easyload.service

import android.view.View
import com.xu.easyload.state.BaseState


interface ILoadService {
    /**
     * 成功
     */
    fun showSuccess()

    /**
     * 展示指定的状态
     */
    fun showState(clState: Class<out BaseState>)

    /**
     * 获取父布局
     * 一般在Fragment中会用到这个方法
     */
    fun getParentView(): View
}