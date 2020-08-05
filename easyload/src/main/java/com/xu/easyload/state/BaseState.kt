package com.xu.easyload.state

import android.content.Context
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import com.xu.easyload.service.ILoadService
import java.io.Serializable

/**
 * @author 言吾許
 */
abstract class BaseState : Serializable {
    private var view: View? = null
    private var context: Context? = null
    private var onReloadListener: ((iLoadService: ILoadService, clickState: BaseState, view: View) -> Unit)? = null

    companion object {
        /**
         *
         */
        const val NO_RES_LAYOUT = 0
    }

    constructor()

    constructor(view: View, context: Context, onReloadListener: ((iLoadService: ILoadService, clickState: BaseState, view: View) -> Unit)? = null) {
        this.view = view
        this.context = context
        this.onReloadListener = onReloadListener
    }

    constructor(context: Context) {
        this.context = context
    }

    internal fun initView(context: Context, onReloadListener: ((iLoadService: ILoadService, clickState: BaseState, view: View) -> Unit)? = null) {
        this.context = context
        this.onReloadListener = onReloadListener
    }

    /**
     * 初始化view
     */
    internal fun getView(loadService: ILoadService, currentState: BaseState): View {
        //原始view因为直接返回，所以没有监听
        if (view != null) {
            return view!!
        }
        val resId = onCreateView()
        if (resId != NO_RES_LAYOUT) {
            view = View.inflate(context, onCreateView(), null)
        }
        view?.setOnClickListener {
            if (canReloadable()) {
                onReloadListener?.invoke(loadService, currentState, it)
            }
        }

        return view!!
    }

    /**
     * 设置布局
     */
    @LayoutRes
    protected abstract fun onCreateView(): Int

    /**
     * 是否能点击重新加载，默认不能false
     */
    open fun canReloadable(): Boolean {
        return false
    }


    /**
     * 绑定view或者移动view到前面
     */
    open fun attachView(context: Context, view: View) {

    }

    /**
     * 解绑view
     * 在remove view的时候调用
     */
    open fun detachView() {

    }


}