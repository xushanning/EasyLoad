package com.example.easyload.ui

import android.os.Handler
import com.example.easyload.BaseActivity
import com.example.easyload.DelayUtil
import com.example.easyload.R
import com.example.easyload.states.ErrorState
import com.example.easyload.states.LoadingState
import com.example.easyload.states.LoadingState2
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.listener.OnMultiListener
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

        DelayUtil.delay(service, SuccessState::class.java, 2000)
        smart_refresh.setOnMultiListener(object : OnMultiListener {

            override fun onFooterMoving(footer: RefreshFooter?, isDragging: Boolean, percent: Float, offset: Int, footerHeight: Int, maxDragHeight: Int) {
            }

            override fun onHeaderStartAnimator(header: RefreshHeader?, headerHeight: Int, maxDragHeight: Int) {
                // refresh show loading
                service.showState(LoadingState::class.java)
                Handler().postDelayed({
                    service.showState(SuccessState::class.java)
                    smart_refresh.finishRefresh()
                }, 3000)
            }

            override fun onFooterReleased(footer: RefreshFooter?, footerHeight: Int, maxDragHeight: Int) {
            }

            override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {

            }


            override fun onHeaderMoving(header: RefreshHeader?, isDragging: Boolean, percent: Float, offset: Int, headerHeight: Int, maxDragHeight: Int) {
            }

            override fun onFooterFinish(footer: RefreshFooter?, success: Boolean) {
            }

            override fun onFooterStartAnimator(footer: RefreshFooter?, footerHeight: Int, maxDragHeight: Int) {
            }

            override fun onHeaderReleased(header: RefreshHeader?, headerHeight: Int, maxDragHeight: Int) {
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
            }

            override fun onHeaderFinish(header: RefreshHeader?, success: Boolean) {
            }
        })
    }
}