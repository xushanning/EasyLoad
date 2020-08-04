package com.example.easyload.states

import android.content.Context
import android.view.View
import com.example.easyload.R
import com.ldoublem.loadingviewlib.LVChromeLogo
import com.xu.easyload.state.BaseState

/**
 * @author 许 on 2020/8/4.
 */
class LoadingState : BaseState() {
    override fun onCreateView(): Int {
        return R.layout.view_easy_load_loading
    }

    override fun attachView(context: Context, view: View) {
        val chrome = view.findViewById<LVChromeLogo>(R.id.lv_chromeLogo)
        chrome.startAnim()
    }
}