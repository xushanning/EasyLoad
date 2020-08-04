package com.example.easyload.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.easyload.BaseActivity
import com.example.easyload.R
import kotlinx.android.synthetic.main.activity_fragment.*

/**
 * @author 言吾許
 * 多fragment
 */
class FragmentActivity : BaseActivity() {

    override fun setLayout(): Int {
        return R.layout.activity_fragment
    }

    override fun initView() {
        val fragmentList = ArrayList<Fragment>().apply {
            add(FragmentA())
            add(FragmentB())
        }
        val titleList = ArrayList<String>().apply {
            add("FragmentA")
            add("FragmentB")
        }
        val pagerAdapter = MyPagerAdapter(supportFragmentManager, fragmentList, titleList)
        vp_fragment.adapter = pagerAdapter
        tl_fragment.setupWithViewPager(vp_fragment)
    }

    class MyPagerAdapter constructor(fm: FragmentManager, private val fragmentList: List<Fragment>, private val titleList: List<String>) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

    }
}