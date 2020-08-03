package com.example.easyload.states

import android.content.Context
import android.view.View
import com.example.easyload.R
import com.xu.easyload.state.BaseState
import io.supercharge.shimmerlayout.ShimmerLayout

/**
 * @author 言吾許
 */
class PlaceHolderState : BaseState() {
    /**
     * 设置布局
     */
    override fun onCreateView(): Int {
        return R.layout.view_easy_load_shimmer
    }

    override fun attachView(context: Context, view: View) {
        val shimmer = view.findViewById<ShimmerLayout>(R.id.shimmer_layout)
        shimmer.startShimmerAnimation()
    }
}