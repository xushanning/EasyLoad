package com.xu.easyload.listener

import android.view.View
import com.xu.easyload.service.ILoadService
import com.xu.easyload.state.BaseState
 import java.io.Serializable

/**
 * @author xu
 */
interface OnReloadListener:Serializable {
    fun onReload(iLoadService: ILoadService, clickState: BaseState, view: View)
}
