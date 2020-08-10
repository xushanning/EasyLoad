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
import kotlinx.android.synthetic.main.activity_normal.*

/**
 * @author 言吾許
 */
class NormalActivity : BaseActivity() {

    companion object {
        private val tag = "_" + NormalActivity::class.java.simpleName
    }

    override fun setLayout(): Int {
        return R.layout.activity_normal
    }

    override fun initView() {

        val service = EasyLoad.initLocal()
                .addLocalState(PlaceHolderState())
                .showDefault(true)
                .setLocalDefaultState(PlaceHolderState::class.java)
                .inject(this) {
                    setOnReloadListener { iLoadService, clickState, view ->
                        when (clickState) {
                            is PlaceHolderState ->
                                Log.d(tag, "点击的是PlaceHolderState")
                            is ErrorState ->
                                Log.d(tag, "点击的是ErrorState")
                            is SuccessState ->
                                Log.d(tag, "点击的是SuccessState")
                        }

                        //可以在子线程中使用
                        Thread(Runnable {
                            iLoadService.showState(PlaceHolderState::class.java)
                            SystemClock.sleep(4000)
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
        DelayUtil.delay(service, ErrorState::class.java)
    }
}