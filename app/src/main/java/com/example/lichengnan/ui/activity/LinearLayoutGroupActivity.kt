package com.example.lichengnan.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.lichengnan.BaseActivity
import com.example.lichengnan.R
import com.example.lichengnan.ui.fragment.EchelonFragment
import com.example.lichengnan.ui.fragment.PickerFragment
import com.example.lichengnan.ui.fragment.SlideFragment
import java.util.*

/**

 * @author：gaohangbo on 2020/9/11 09:29

 */
class LinearLayoutGroupActivity : BaseActivity() {

     lateinit var mTvTitle: TextView
     lateinit var mToolbar: Toolbar
     var mFragmentManager: FragmentManager? = null
     var mFragments: MutableList<Fragment>  = ArrayList() //存储所有的Fragment对象
     val mManagerNames: MutableList<String> = ArrayList() //存储与Fragment对应的LayoutManager的名称
     var mCurrentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layoutmanagergroup)
        mToolbar = findViewById(R.id.tool_bar)
        setSupportActionBar(mToolbar)
        mTvTitle = findViewById(R.id.tv_title)
        mFragmentManager = supportFragmentManager

        val echelonFragment = EchelonFragment() //梯形布局

        mFragments.add(echelonFragment)
        mManagerNames.add("EchelonLayoutManager")

        val pickerFragment= PickerFragment() //选择器布局

        mFragments.add(pickerFragment)
        mManagerNames.add("PickerLayoutManager")

        val slideFragment = SlideFragment() //滑动布局

        mFragments.add(slideFragment)

        mManagerNames.add("SlideLayoutManager")
        mFragmentManager!!.beginTransaction()
                .add(R.id.container_layout, mFragments[0])
                .add(R.id.container_layout, mFragments[1])
                .add(R.id.container_layout, mFragments[2])
                .hide(mFragments[2])
                .hide(mFragments[1])
                .show(mFragments[0])
                .commit()

        if(mFragments.isNotEmpty()){
            mCurrentFragment = mFragments[0]
            mTvTitle.text = mManagerNames[0]
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LinearLayoutGroupActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_0 -> switchFragment(0)
            R.id.item_1 -> switchFragment(1)
            R.id.item_2 -> switchFragment(2)
            R.id.item_3 -> startActivity(Intent(this@LinearLayoutGroupActivity, SkidRightActivity_1::class.java))
            R.id.item_4 -> startActivity(Intent(this@LinearLayoutGroupActivity, BannerActivity::class.java))
            R.id.item_5 -> startActivity(Intent(this@LinearLayoutGroupActivity, ViewPagerLayoutManagerActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun switchFragment(position: Int) {
        mFragmentManager!!.beginTransaction()
                //转场动画
//               .setCustomAnimations(R.anim.fragment_enter_in,
//                R.anim.fragment_enter_out)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .hide(mCurrentFragment!!)
                .show(mFragments[position])
                .commit()
        mCurrentFragment = mFragments[position]
        mTvTitle!!.text = mManagerNames[position]
    }
}