package com.example.easyload.ui

import android.os.Handler
import android.os.SystemClock
import com.example.easyload.BaseActivity
import com.example.easyload.DelayUtil
import com.example.easyload.R
import com.example.easyload.states.ErrorState
import com.example.easyload.states.LoadingState
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
        val imgService = EasyLoad.initLocal()
                .inject(img_view) {
                    setOnReloadListener { iLoadService, clickState, view ->
                        iLoadService.showState(LoadingState::class.java)
                        Handler().postDelayed({
                            iLoadService.showState(SuccessState::class.java)
                        }, 3000)
                    }
                }
        DelayUtil.delay(imgService, ErrorState::class.java, 2500)

        val tvService = EasyLoad.initLocal()
                .inject(tv_long)
        DelayUtil.delay(tvService, SuccessState::class.java, 1000)

        val tvService2 = EasyLoad.initLocal()
                .inject(tv_long2)
        DelayUtil.delay(tvService2, SuccessState::class.java, 1000)
    }
}