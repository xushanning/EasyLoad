package com.example.easyload

import android.content.Intent
import android.util.Log
import com.example.easyload.ui.NormalActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun setLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {

        //注入activity
        bt_inject_activity.setOnClickListener {
            Log.d("main", "跳转")
            startActivity(Intent(this, NormalActivity::class.java))
        }
        //保留appbar
        bt_keep_bar.setOnClickListener {

        }
    }
}