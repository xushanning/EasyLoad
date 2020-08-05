package com.example.easyload.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.LayoutInflaterCompat
import androidx.core.view.updateLayoutParams
import com.example.easyload.BaseActivity
import com.example.easyload.DelayUtil
import com.example.easyload.R
import com.example.easyload.states.ErrorState
import com.example.easyload.states.LoadingState
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
//        val imgService = EasyLoad.initLocal()
//                .inject(img_view) {
//                    setOnReloadListener { iLoadService, clickState, view ->
//                        iLoadService.showState(LoadingState::class.java)
//                        Handler().postDelayed({
//                            iLoadService.showState(SuccessState::class.java)
//                        }, 3000)
//                    }
//                }
//        DelayUtil.delay(imgService, ErrorState::class.java, 3000)

//        val tvService = EasyLoad.initLocal()
//                .inject(tv_long)
//        DelayUtil.delay(tvService, SuccessState::class.java, 3000)
//
//        val tvService2 = EasyLoad.initLocal()
//                .inject(tv_long2)
//        DelayUtil.delay(tvService2, SuccessState::class.java, 3000)
        img_view.setOnClickListener {
            println("图片被点击了")
        }
        tv_long.setOnClickListener {
            view = View.inflate(this, R.layout.view_easy_load_loading, null)
            val imgParams = img_view.layoutParams as ConstraintLayout.LayoutParams
            val params = ConstraintLayout.LayoutParams(imgParams)
            cl_view.addView(view, params)
            view.setOnClickListener {
                println("loading被点击了")
            }
        }
        tv_long2.setOnClickListener {
            view.visibility = View.INVISIBLE
        }
    }
}