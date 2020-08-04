package com.xu.easyload.service

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
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


    private lateinit var parentView: ViewGroup

    private lateinit var mContext: Context

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
        parentView = target.findViewById(android.R.id.content)
        //activity初始化状态就一个view
        val originalView = parentView.getChildAt(0)
        //originalLayoutParams = originalView.layoutParams
        initStates(builder, originalView, target)
    }


    private fun initViewTarget(target: View, builder: EasyLoad.Builder) {

        var childIndex = 0
        //最顶级的容器，为null，说明是Fragment，那么将FrameLayout作为view给到Fragment
        //不为null，在contentParent和childView之间加一层FrameLayout
        val contentParent = if (target.parent == null) {
            null
        } else {
            target.parent as ViewGroup
        }
        contentParent?.removeView(target)
        val oldLayoutParams = target.layoutParams
        parentView = FrameLayout(target.context)
        parentView.layoutParams = oldLayoutParams
        contentParent?.addView(parentView, contentParent.indexOfChild(target), oldLayoutParams)
//        if (parentViewGroup.javaClass == Class.forName("androidx.constraintlayout.widget.ConstraintLayout") || parentViewGroup.javaClass == RelativeLayout::class.java) {
//            //constraintLayout和RelativeLayout特殊处理
//            needSpecialHandle = true
//            targetParams = target.layoutParams
//            parentView = parentViewGroup
//        } else {
//            val index = parentViewGroup.indexOfChild(target)
//            parentView = FrameLayout(target.context)
//            parentView.layoutParams = target.layoutParams
//            parentViewGroup.removeView(target)
//            parentViewGroup.addView(parentView, index)
//            val childParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//            parentView.addView(target, childParams)
//        }
        initStates(builder, target, target.context)
    }


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
        val showDefault=builder.showDefault
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
        val successState = SuccessState(targetView, mContext, reloadListener)
        mStates[successState.javaClass] = successState
        //只要是localDefaultState不为空，那么local生效
        if (localDefaultState != null) {
            showDefault(localDefaultState)
            return;
        }
        //只有local为null，并且global不为null的情况下，global才生效
        if (globalDefaultState != null) {
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
        val childView = state!!.getView(this, state)
        //线程切换
        if (Looper.myLooper() == Looper.getMainLooper()) {
            show(state, childView)
        } else {
            Handler(Looper.getMainLooper()).post {
                show(state, childView)
            }
        }
    }

    private fun show(state: BaseState, childView: View) {
        parentView.removeAllViews()
        onStateChangedListener?.invoke(childView, state)
        //相同，不变
        if (currentOtherStateView == childView) {
            return
        }
        if (state !is SuccessState) {
            childView.layoutParams = if (needSpecialHandle) {
                targetParams
            } else {
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }
        }

        //增加新的状态view
        //todo 性能没有直接显示高
        parentView.addView(childView)
        state.attachView(mContext, childView)
        currentOtherStateView = childView
        Log.d("tag", parentView.childCount.toString())
    }
}