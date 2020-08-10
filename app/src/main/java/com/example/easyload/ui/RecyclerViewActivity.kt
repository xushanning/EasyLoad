package com.example.easyload.ui

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easyload.BaseActivity
import com.example.easyload.R
import kotlinx.android.synthetic.main.activity_recyclerview.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author 言吾許
 */
class RecyclerViewActivity : BaseActivity() {
    override fun setLayout(): Int {
        return R.layout.activity_recyclerview
    }

    override fun initView() {
        rv_img.layoutManager = LinearLayoutManager(this)
        rv_img.adapter = RecyclerViewAdapter(getData())
    }

    private fun getData(): List<String> {
        val result = ArrayList<String>()
        for (item in 0..100) {
            result.add(String.format(Locale.CHINA, "https://www.thiswaifudoesnotexist.net/example-%d.jpg", (Math.random() * 100000).toInt()))
        }
        return result
    }
}