package com.example.easyload.ui

import android.os.SystemClock
import com.example.easyload.BaseActivity
import com.example.easyload.DelayUtil
import com.example.easyload.R
import com.example.easyload.states.ErrorState
import com.example.easyload.states.LoadingState
import com.example.easyload.states.PlaceHolderState
import com.xu.easyload.EasyLoad
import com.xu.easyload.state.SuccessState
import kotlinx.android.synthetic.main.activity_view.*

/**
 * @author 许 on 2020/8/4.
 */
class ViewActivity : BaseActivity() {
    override fun setLayout(): Int {
        return R.layout.activity_view
    }

    override fun initView() {
        val service = EasyLoad.initLocal()
                .inject(img_view) {
                    setOnReloadListener { iLoadService, clickState, view ->
                        iLoadService.showState(LoadingState::class.java)
                        SystemClock.sleep(500)
                        iLoadService.showState(SuccessState::class.java)
                    }
                }
        DelayUtil.delay(service, SuccessState::class.java, 3000)
    }
}