package com.example.easyload.ui

import com.example.easyload.BaseActivity
import com.example.easyload.DelayUtil
import com.example.easyload.R
import com.example.easyload.states.ErrorState
import com.xu.easyload.EasyLoad
import com.xu.easyload.state.SuccessState
import kotlinx.android.synthetic.main.activity_smart_refresh.*

/**
 * @author 言吾許
 */
class SmartRefreshActivity : BaseActivity() {
    override fun setLayout(): Int {
        return R.layout.activity_smart_refresh
    }

    override fun initView() {
        val service = EasyLoad.initLocal()
                .inject(tv_smart_refresh)

        // DelayUtil.delay(service, SuccessState::class.java, 2000)
    }
}