package com.example.easyload.ui

import com.example.easyload.BaseActivity
import com.example.easyload.DelayUtil
import com.example.easyload.R
import com.example.easyload.states.ErrorState
import com.example.easyload.states.PlaceHolderState
import com.xu.easyload.EasyLoad

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
                .setOnReloadListener { iLoadService, clickState, view ->

                }
                .setOnStateChangeListener { view, currentState ->

                }
                .inject(this) {
                    setOnReloadListener { iLoadService, clickState, view ->

                    }
                }
        DelayUtil.delay(service, ErrorState::class.java, 4000)
    }
}