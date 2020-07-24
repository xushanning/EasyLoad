package com.xu.easyload

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment
import com.xu.easyload.listener.OnReloadListener
import com.xu.easyload.listener.OnStateChangeListener
import com.xu.easyload.service.ILoadService
import com.xu.easyload.service.LoadService
import com.xu.easyload.state.BaseState
import com.xu.easyload.listener.MyListener
import java.io.*
import java.util.*

/**
 * @author 言吾許
 */
class EasyLoad private constructor() {

    companion object {
        /**
         * 是否已经初始化
         */
        private var hasInit = false

        /**
         * 单例
         */
        private val instance: EasyLoad by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            EasyLoad()
        }

        /**
         * 单例
         */
        private val builder: Builder by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Builder()
        }


        /**
         * 初始化全局
         * 只能初始化一次
         */
        fun initGlobal(): GlobalBuilder {
            if (hasInit) {
                throw IllegalAccessException("Only allow to initialize once")
            }
            hasInit = true
            return GlobalBuilder()
        }

        /**
         * 初始化局部
         */
        fun initLocal(): LocalBuilder {
            return LocalBuilder()
        }
    }

    /**
     * 注入Activity
     * @param target 目标Activity
     */
    fun inject(target: Activity): ILoadService {
        return LoadService(target, resetLocalState())
    }

    /**
     * 注入View
     * @param target 目标View
     */
    fun inject(target: View): ILoadService {
        return LoadService(target, resetLocalState())
    }

    /**
     * 注入Fragment
     * @param target 目标Fragment
     */
    fun inject(target: Fragment): ILoadService {
        return LoadService(target, resetLocalState())
    }

    private fun resetLocalState(): Builder {
        val cloneBuilder = builder.copy()
        builder.localDefaultState = null
        builder.onReloadListener = null
        builder.onStateChangeListener = null
        builder.localStates.clear()
        return cloneBuilder
    }

    /**
     * 局部builder
     */
    class LocalBuilder internal constructor() {
        /**
         *添加局部的state
         */
        fun addLocalState(state: BaseState) = apply {
            builder.addLocalState(state)
        }

        /**
         * 添加全局的默认的state
         */
        fun setLocalDefaultState(localDefault: Class<out BaseState>) = apply {
            builder.setLocalDefaultState(localDefault)
        }

        /**
         * 设置重新加载监听
         */
        fun setOnReloadListener(onReloadListener: OnReloadListener) = apply {
            builder.setOnReloadListener(onReloadListener)
        }

        /**
         * 设置重新加载监听
         */
        fun setOnStateChangeListener(onStateChangeListener: OnStateChangeListener) = apply {
            builder.setOnStateChangeListener(onStateChangeListener)
        }

        /**
         * 注入 Activity
         */
        fun inject(target: Activity): ILoadService {
            return instance.inject(target)
        }

        /**
         * 注入 Fragment
         */
        fun inject(target: Fragment): ILoadService {
            return instance.inject(target)
        }

        /**
         * 注入 View
         */
        fun inject(target: View): ILoadService {
            return instance.inject(target)
        }
    }

    /**
     * 全局builder
     */
    class GlobalBuilder internal constructor() {
        /**
         * 添加局部状态
         */
        fun addGlobalState(state: BaseState) = apply {
            builder.addGlobalState(state)
        }

        /**
         * 添加局部默认
         */
        fun setGlobalDefaultState(defaultState: Class<out BaseState>) = apply {
            builder.setGlobalDefaultState(defaultState)
        }

    }

    class Builder internal constructor() : Serializable {
        /**
         * 全局的status
         */
        val globalStates: MutableList<BaseState> = ArrayList()

        /**
         * 局部status
         */
        val localStates: MutableList<BaseState> = ArrayList()

        /**
         * 默认的全局status
         */
        var globalDefaultState: Class<out BaseState>? = null

        /**
         * 默认的局部status
         */
        var localDefaultState: Class<out BaseState>? = null

        /**
         * 重新加载监听
         */
        var onReloadListener: OnReloadListener? = null

        /**
         * 状态改变监听
         */
        internal var onStateChangeListener: OnStateChangeListener? = null

        /**
         * 添加全局的state
         */
        internal fun addGlobalState(state: BaseState) = apply {
            if (!globalStates.contains(state)) {
                globalStates.add(state)
            }
        }

        /**
         * 设置默认的status
         * 后设置会把前面设置的覆盖掉
         */
        internal fun setGlobalDefaultState(defaultStatus: Class<out BaseState>) = apply {
            this.globalDefaultState = defaultStatus
        }


        /**
         *添加局部的state
         */
        internal fun addLocalState(state: BaseState) = apply {
            if (!localStates.contains(state)) {
                localStates.add(state)
            }
        }

        /**
         * 添加全局的默认的state
         */
        internal fun setLocalDefaultState(localDefault: Class<out BaseState>) = apply {
            this.localDefaultState = localDefault
        }

        /**
         * 设置重新加载监听
         */
        internal fun setOnReloadListener(onReloadListener: OnReloadListener) = apply {
            this.onReloadListener = onReloadListener
        }


        /**
         * 设置重新加载监听
         */
        internal fun setOnStateChangeListener(onStateChangeListener: OnStateChangeListener) = apply {
            this.onStateChangeListener = onStateChangeListener
        }

        /**
         * 复制对象
         */
        internal fun copy(): Builder {
            val bos = ByteArrayOutputStream()
            val oos = ObjectOutputStream(bos)
            oos.writeObject(this@Builder)
            val bis = ByteArrayInputStream(bos.toByteArray())
            val ois = ObjectInputStream(bis)
            return ois.readObject() as Builder
        }

    }


}