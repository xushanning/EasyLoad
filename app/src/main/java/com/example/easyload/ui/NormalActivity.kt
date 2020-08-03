package com.example.easyload.ui

import android.os.SystemClock
import android.util.Log
import com.example.easyload.BaseActivity
import com.example.easyload.DelayUtil
import com.example.easyload.R
import com.example.easyload.states.ErrorState
import com.example.easyload.states.PlaceHolderState
import com.xu.easyload.EasyLoad
import com.xu.easyload.state.SuccessState
import java.util.logging.Logger

/**
 * @author 言吾許
 */
class NormalActivity : BaseActivity() {

    companion object {
        private val tag = "normal"
    }

    override fun setLayout(): Int {
        return R.layout.activity_normal
    }

    override fun initView() {

        val service = EasyLoad.initLocal()
                .setLocalDefaultState(PlaceHolderState::class.java)
                .inject(this) {
                    setOnReloadListener { iLoadService, clickState, view ->
                        //可以在子线程中使用
                        Thread(Runnable {
                            iLoadService.showState(PlaceHolderState::class.java)
                            SystemClock.sleep(500)
                            iLoadService.showState(SuccessState::class.java)
                        }).start()
                    }
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
        DelayUtil.delay(service, ErrorState::class.java, 3000)
    }
}