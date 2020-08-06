package com.example.easyload.ui

import android.os.Handler
import android.view.Gravity
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.easyload.BaseActivity
import com.example.easyload.DelayUtil
import com.example.easyload.R
import com.example.easyload.states.ErrorState
import com.example.easyload.states.LoadingState
import com.example.easyload.states.LoadingState2
import com.xu.easyload.EasyLoad
import com.xu.easyload.state.SuccessState
import kotlinx.android.synthetic.main.activity_view.*

/**
 * @author 许 on 2020/8/4.
 */
class ViewActivity : BaseActivity() {
    lateinit var view: View
    override fun setLayout(): Int {
        return R.layout.activity_view
    }

    override fun initView() {
        //ConstraintLayout img
//        val sClImg = EasyLoad.initLocal()
//                .inject(img_view) {
//                    setOnReloadListener { iLoadService, clickState, view ->
//                        iLoadService.showState(LoadingState::class.java)
//                        Handler().postDelayed({
//                            iLoadService.showState(SuccessState::class.java)
//                        }, 3000)
//                    }
//                }
//        DelayUtil.delay(sClImg, ErrorState::class.java, 3500)

//        val sLayer = EasyLoad.initLocal()
//                .addLocalState(LoadingState2())
//                .setLocalDefaultState(LoadingState2::class.java)
//                .inject(cl_child)
//
//        DelayUtil.delay(sLayer, SuccessState::class.java, 2000)

//        val sRelativeTv = EasyLoad.initLocal()
//                .inject(tv_long2)
//        DelayUtil.delay(sRelativeTv, SuccessState::class.java, 4500)
//
//        val sRelativeImg = EasyLoad.initLocal()
//                .addLocalState(LoadingState2())
//                .setLocalDefaultState(LoadingState2::class.java)
//                .inject(img_relative) {
//                    setOnReloadListener { iLoadService, clickState, view ->
//
//                    }
//                }
//        DelayUtil.delay(sRelativeImg, SuccessState::class.java, 5000)
        Handler().postDelayed({
            val sLayer = EasyLoad.initLocal()
                    .addLocalState(LoadingState2())
                    .setLocalDefaultState(LoadingState2::class.java)
                    .inject(cl_child)
            DelayUtil.delay(sLayer, SuccessState::class.java, 2000)

//            view = View.inflate(this, R.layout.view_easy_load_loading, null)
//            val imgParams = cl_child.layoutParams as ConstraintLayout.LayoutParams
//            val params = ConstraintLayout.LayoutParams(imgParams)
//            cl_view.addView(view, -1, params)
        }, 100)

        tv_long2.setOnClickListener {
//            view = View.inflate(this, R.layout.view_easy_load_loading, null)
//            val imgParams = cl_child.layoutParams as ConstraintLayout.LayoutParams
//            val params = ConstraintLayout.LayoutParams(imgParams)
//            cl_view.addView(view, -1, params)

//            val sClTv = EasyLoad.initLocal()
//                    .inject(tv_long)
        }

        v_bottom.setOnClickListener {
            view.visibility = View.INVISIBLE
        }
    }
}