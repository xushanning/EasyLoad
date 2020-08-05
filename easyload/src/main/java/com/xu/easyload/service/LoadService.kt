package com.xu.easyload.service

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.contains
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

    /**
     * 父容器ViewGroup
     */
    private lateinit var parentView: ViewGroup

    /**
     * 上下文
     */
    private lateinit var mContext: Context

    /**
     * 当前的state
     */
    private var currentState: BaseState? = null

    /**
     * 存放state
     */
    private val mStates = HashMap<Class<out BaseState>, BaseState>()

    /**
     * 当前的view
     */
    private var currentStateView: View? = null

    /**
     * 状态变更监听
     */
    private var onStateChangedListener: ((view: View?, currentState: BaseState) -> Unit)? = null

    /**
     * 是否需要特殊处理
     * 因为ConstraintLayout和RelativeLayout的布局方式会和id有关，所以需要特殊处理
     */
    private var needSpecialHandle = false

    /**
     * 特殊处理的LayoutParams
     */
    private lateinit var specialHandleParams: ViewGroup.LayoutParams


    private fun initActivityTarget(target: Activity, builder: EasyLoad.Builder) {
        parentView = target.findViewById(android.R.id.content)
        val originalView = parentView.getChildAt(0)
        initStates(builder, originalView, target)
    }


    private fun initViewTarget(target: View, builder: EasyLoad.Builder) {
        //最顶级的容器，为null，说明是Fragment，那么将FrameLayout作为view给到Fragment
        //不为null，在contentParent和childView之间加一层FrameLayout
        val contentParent = if (target.parent == null) {
            null
        } else {
            target.parent as ViewGroup
        }
        //ConstraintLayout and RelativeLayout Need special handle
        needSpecialHandle = contentParent != null && (contentParent.javaClass == Class.forName("androidx.constraintlayout.widget.ConstraintLayout") || contentParent.javaClass == RelativeLayout::class.java)
        if (needSpecialHandle) {
            specialHandle(target, builder)
            return
        }
        contentParent?.removeView(target)
        val oldLayoutParams = target.layoutParams
        parentView = FrameLayout(target.context)
        parentView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        contentParent?.addView(parentView, contentParent.indexOfChild(target), oldLayoutParams)
        initStates(builder, target, target.context)
    }

    /**
     *Special treatment for  ConstraintLayout or RelativeLayout
     */
    private fun specialHandle(target: View, builder: EasyLoad.Builder) {
        needSpecialHandle = true
        parentView = target.parent as ViewGroup
        specialHandleParams = target.layoutParams

        initStates(builder, target, target.context)
    }

    /**
     * 获取父容器
     * fragment会用到这个方法
     */
    override fun getParentView(): View {
        return parentView
    }

    private fun initStates(builder: EasyLoad.Builder, targetView: View, mContext: Context) {
        this.mContext = mContext
        val globalStates = builder.globalStates
        val localStates = builder.localStates
        val reloadListener = builder.onReloadListener
        val localDefaultState = builder.localDefaultState
        val globalDefaultState = builder.globalDefaultState
        val showDefault = builder.showDefault
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
        val successState = SuccessState(targetView, mContext)
        mStates[successState.javaClass] = successState
        //if localDefaultState is not null and allow show default,then show the global default
        if (localDefaultState != null && showDefault) {
            showState(localDefaultState)
            return;
        }
        //if localDefaultState is null and globalDefaultState is not null and allow show default,then show the global default
        if (globalDefaultState != null && showDefault) {
            showState(globalDefaultState)
        }
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
            throw NullPointerException("${clState.simpleName} not instantiated,you must configure ${clState.simpleName} globally or locally")
        }
        val state = mStates[clState]
        if (Looper.myLooper() == Looper.getMainLooper()) {
            show(state!!)
        } else {
            Handler(Looper.getMainLooper()).post {
                show(state!!)
            }
        }
    }

    private fun show(state: BaseState) {
        if (needSpecialHandle) {
            showSpecial(state)
        } else {
            showOrdinary(state)
        }
    }

    /**
     * 展示普通布局状态
     */
    private fun showOrdinary(state: BaseState) {
        val childView = state.getView(this, state)
        if (currentStateView == childView) {
            return
        }
        currentState?.detachView()
        parentView.removeAllViews()
        onStateChangedListener?.invoke(childView, state)
        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        parentView.addView(childView, layoutParams)
        state.attachView(mContext, childView)
        currentStateView = childView
        currentState = state
    }

    /**
     * 展示特殊的（ConstraintLayout RelativeLayout）
     */
    private fun showSpecial(state: BaseState) {

        val childView = state.getView(this, state)
        if (currentStateView == childView) {
            return
        }
        onStateChangedListener?.invoke(childView, state)
        currentStateView = childView
        currentState = state
        if (parentView.indexOfChild(childView) != -1) {
            parentView.bringChildToFront(childView)
            return
        }
        if (state !is SuccessState) {
            childView.layoutParams = specialHandleParams
            parentView.addView(childView)
            state.attachView(mContext, childView)
        }
    }
}