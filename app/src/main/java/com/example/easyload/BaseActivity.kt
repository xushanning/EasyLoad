package com.example.easyload

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author 言吾許
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())
        initView()
    }

    abstract fun setLayout(): Int
    abstract fun initView()
}