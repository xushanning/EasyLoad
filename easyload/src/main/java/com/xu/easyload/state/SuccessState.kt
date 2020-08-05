package com.xu.easyload.state

import android.content.Context
import android.view.View
import com.xu.easyload.service.ILoadService

/**
 * 成功
 */
class SuccessState : BaseState {

    constructor(view: View, context: Context) : super(view, context, null)

    constructor(context: Context) : super(context)

    /**
     * 设置布局
     */
    override fun onCreateView(): Int {
        return NO_RES_LAYOUT
    }
}