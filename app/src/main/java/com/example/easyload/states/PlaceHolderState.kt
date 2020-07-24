package com.example.easyload.states

import com.example.easyload.R
import com.xu.easyload.state.BaseState

/**
 * @author 言吾許
 */
class PlaceHolderState : BaseState() {
    /**
     * 设置布局
     */
    override fun onCreateView(): Int {
        return R.layout.view_easy_load_place_holder
    }
}