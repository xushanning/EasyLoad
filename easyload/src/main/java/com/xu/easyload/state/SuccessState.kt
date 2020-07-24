package com.xu.easyload.state

import android.content.Context
import android.view.View
import com.xu.easyload.service.ILoadService

/**
 * 成功
 */
class SuccessState constructor(view: View, context: Context, onReloadListener: ((iLoadService: ILoadService, clickState: BaseState, view: View) -> Unit)? = null) : BaseState(view, context, onReloadListener) {
    /**
     * 设置布局
     */
    override fun onCreateView(): Int {
        return NO_RES_LAYOUT
    }
}