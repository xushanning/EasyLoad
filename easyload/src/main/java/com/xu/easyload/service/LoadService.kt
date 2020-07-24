package com.xu.easyload.service

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.xu.easyload.EasyLoad
import com.xu.easyload.state.BaseState
import com.xu.easyload.state.SuccessState


class LoadService : ILoadService {

    constructor(target: View, builder: EasyLoad.Builder) {
        initViewTarget(target, builder)
    }

    constructor(target: Activity, builder: EasyLoad.Builder) {
        initActivityTarget(target, builder)
    }

    constructor(target: Fragment, builder: EasyLoad.Builder) {
        initFragmentTarget(target, builder)
    }

    private lateinit var container: ViewGroup

    /**
     * 目标view的params
     */
    private var targetParams: ViewGroup.LayoutParams? = null


    /**
     * 存放state
     */
    private val mStates = HashMap<Class<out BaseState>, BaseState>()

    /**
     * 除去成功的当前的view
     */
    private var currentOtherStateView: View? = null

    private var onStateChangedListener: ((view: View, currentState: BaseState) -> Unit)? = null

    /**
     * 是否需要特殊处理
     */
    private var needSpecialHandle = false


    private fun initActivityTarget(target: Activity, builder: EasyLoad.Builder) {

        container = target.findViewById(android.R.id.content)
        //activity初始化状态就一个view
        val originalView = container.getChildAt(0)
        //originalLayoutParams = originalView.layoutParams
        initStates(builder, originalView, target)
    }

    private fun initFragmentTarget(target: Fragment, builder: EasyLoad.Builder) {

    }

    private fun initViewTarget(target: View, builder: EasyLoad.Builder) {

        val parentViewGroup = target.parent as ViewGroup
        val mContext = target.context
        if (parentViewGroup.javaClass == Class.forName("androidx.constraintlayout.widget.ConstraintLayout") || parentViewGroup.javaClass == RelativeLayout::class.java) {
            //constraintLayout和RelativeLayout特殊处理
            needSpecialHandle = true
            targetParams = target.layoutParams
            container = parentViewGroup
        } else {
            val index = parentViewGroup.indexOfChild(target)
            container = FrameLayout(target.context)
            container.layoutParams = target.layoutParams
            parentViewGroup.removeView(target)
            parentViewGroup.addView(container, index)
            val childParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            container.addView(target, childParams)
        }
        initStates(builder, target, mContext)
    }


    private fun initStates(builder: EasyLoad.Builder, originalView: View, mContext: Context) {

        val globalStates = builder.globalStates
        val localStates = builder.localStates
        val reloadListener = builder.onReloadListener
        val localDefaultState = builder.localDefaultState
        val globalDefaultState = builder.globalDefaultState
        this.onStateChangedListener = builder.onStateChangeListener
        if (globalStates.isEmpty() && localStates.isEmpty()) {
            throw IllegalArgumentException("globalState和localState必须设置其一~！")
        }
        //全局state初始化
        globalStates.forEach {
            it.initView(mContext, reloadListener)
            mStates[it.javaClass] = it
        }
        //local会把全局覆盖
        localStates.forEach {
            it.initView(mContext, reloadListener)
            mStates[it.javaClass] = it
        }
        //成功state
        val successState = SuccessState(originalView, mContext, reloadListener)
        mStates[successState.javaClass] = successState
        //只要是localDefaultState不为空，那么local生效
        if (localDefaultState != null) {
            showDefault(localDefaultState)
        }
        //只有local为null，并且global不为null的情况下，global才生效
        if (globalDefaultState != null && localDefaultState == null) {
            showDefault(globalDefaultState)
        }
    }

    /**
     * 展示默认的state
     */
    private fun showDefault(defaultState: Class<out BaseState>) {
        if (!mStates.containsKey(defaultState)) {
            throw IllegalArgumentException("未实例化${defaultState.simpleName}")
        }
        //存在默认，那么展示默认
        showState(defaultState)
    }

    /**
     * 成功
     */
    override fun showSuccess() {
        showState(SuccessState::class.java)
    }

    /**
     * 展示state
     */
    override fun showState(clState: Class<out BaseState>) {
        if (!mStates.containsKey(clState)) {
            throw IllegalArgumentException("未实例化${clState.simpleName}")
        }

        //核心
        val state = mStates[clState]
        val view = state!!.getView(this, state)
        onStateChangedListener?.invoke(view, state)
        if (state is SuccessState) {
            //成功->移除状态view
            container.removeView(currentOtherStateView)
            Log.d("tag", container.childCount.toString())
        } else {
            //相同，不变
            if (currentOtherStateView == view) {
                return
            }
            //不同，移除状态view
            container.removeView(currentOtherStateView)
            view.layoutParams = if (needSpecialHandle) {
                targetParams
            } else {
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }
            //增加新的状态view
            //todo 性能没有直接显示高
            container.addView(view)
            currentOtherStateView = view
        }

    }
}