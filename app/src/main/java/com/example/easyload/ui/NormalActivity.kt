package com.example.easyload.ui

import android.view.View
import com.example.easyload.BaseActivity
import com.example.easyload.DelayUtil
import com.example.easyload.R
import com.example.easyload.states.ErrorState
import com.example.easyload.states.PlaceHolderState
import com.xu.easyload.EasyLoad
import com.xu.easyload.listener.OnReloadListener
import com.xu.easyload.service.ILoadService
import com.xu.easyload.state.BaseState

/**
 * @author 言吾許
 */
class NormalActivity : BaseActivity() {
    override fun setLayout(): Int {
        return R.layout.activity_normal
    }

    override fun initView() {

        val service = EasyLoad.initLocal()
                .setLocalDefaultState(PlaceHolderState::class.java)
//                .addLocalState()
//                .addLocalState()
                .setOnReloadListener(object : OnReloadListener {
                    override fun onReload(iLoadService: ILoadService, clickState: BaseState, view: View) {

                    }
                })
                .inject(this)
        DelayUtil.delay(service, ErrorState::class.java, 4000)
    }
}