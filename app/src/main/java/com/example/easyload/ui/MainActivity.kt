package com.example.easyload.ui

import android.content.Intent
import android.util.Log
import com.example.easyload.BaseActivity
import com.example.easyload.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun setLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {

        //注入activity
        bt_inject_activity.setOnClickListener {
            startActivity(Intent(this, NormalActivity::class.java))
        }
        //注入Fragment
        bt_inject_fragment.setOnClickListener {
            startActivity(Intent(this, FragmentActivity::class.java))
        }
        //注入view
        bt_inject_view.setOnClickListener {
            startActivity(Intent(this, ViewActivity::class.java))
        }
    }
}