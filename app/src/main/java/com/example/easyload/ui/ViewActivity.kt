package com.example.easyload.ui

import android.os.Handler
import com.example.easyload.BaseActivity
import com.example.easyload.DelayUtil
import com.example.easyload.R
import com.example.easyload.states.ErrorState
import com.example.easyload.states.LoadingState
import com.example.easyload.states.LoadingState2
import com.xu.easyload.EasyLoad
import com.xu.easyload.ext.inject
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

        //ConstraintLayout img
        //注入方法①
//        val sClImg = EasyLoad.initLocal()
//                .inject(img_view) {
        //注入方法②
        val sClImg = inject(img_view) {
            setOnReloadListener { iLoadService, clickState, view ->
                println("重新加载")
                iLoadService.showState(LoadingState::class.java)
                Handler().postDelayed({
                    iLoadService.showState(SuccessState::class.java)
                }, 3000)
            }
        }
        DelayUtil.delay(sClImg, ErrorState::class.java, 3500)


        val sRelativeTv = EasyLoad.initLocal()
                .inject(tv_long2)
        DelayUtil.delay(sRelativeTv, SuccessState::class.java, 4500)

//        val sRelativeImg = EasyLoad.initLocal()
//                .addLocalState(LoadingState2())
//                .setLocalDefaultState(LoadingState2::class.java)
//                .inject(img_relative) {
//                    specialSupport(true)
//                    setOnReloadListener { iLoadService, clickState, view ->
//
//                    }
//                }
        val sRelativeImg = inject(img_relative) {
            addLocalState(LoadingState2())
            setLocalDefaultState(LoadingState2::class.java)
            specialSupport(true)
            setOnReloadListener { iLoadService, clickState, view ->

            }
        }


        DelayUtil.delay(sRelativeImg, SuccessState::class.java, 5000)

        val sLayer = EasyLoad.initLocal()
                .addLocalState(LoadingState2())
                .setLocalDefaultState(LoadingState2::class.java)
                //ConstraintLayout 2.0版本以上会如果ConstraintLayout里面再嵌套ConstraintLayout，会出现不显示的问题，可以通过设置specialSupport为true来支持
//                .specialSupport(true)
                .inject(cl_child)
        DelayUtil.delay(sLayer, SuccessState::class.java, 2000)


        val vService = EasyLoad.initLocal()
                .addLocalState(LoadingState2())
                .setLocalDefaultState(LoadingState2::class.java)
                .inject(v_bottom)
        DelayUtil.delay(vService, SuccessState::class.java, 2000)

//        Handler().postDelayed({
//            view = View.inflate(this, R.layout.view_easy_load_loading, null)
//            val imgParams = cl_child.layoutParams as ConstraintLayout.LayoutParams
//            val params = ConstraintLayout.LayoutParams(imgParams)
//            cl_view.addView(view, -1, params)
//        }, 50)


    }
}