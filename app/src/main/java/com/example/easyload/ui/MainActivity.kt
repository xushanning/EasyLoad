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

        //inject activity
        bt_inject_activity.setOnClickListener {
            startActivity(Intent(this, NormalActivity::class.java))
        }
        //inject Fragment
        bt_inject_fragment.setOnClickListener {
            startActivity(Intent(this, FragmentActivity::class.java))
        }
        //inject view
        bt_inject_view.setOnClickListener {
            startActivity(Intent(this, ViewActivity::class.java))
        }
        //SmartRefresh
        bt_inject_smart_refresh.setOnClickListener {
            startActivity(Intent(this, SmartRefreshActivity::class.java))
        }
        //RecyclerView
        bt_recycler_view.setOnClickListener {
            startActivity(Intent(this, RecyclerViewActivity::class.java))
        }

    }
}