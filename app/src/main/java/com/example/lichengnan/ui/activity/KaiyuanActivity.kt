package com.example.lichengnan.ui.activity

import android.content.Context
import android.content.Intent
import com.example.lichengnan.BaseActivity
import com.example.lichengnan.ui.distory.home.HomePageFragment

/**

 * @author：gaohangbo on 2020/9/2 11:16

 */
class KaiyuanActivity: BaseActivity()  {

    private var backPressTime = 0L

    private var homePageFragment: HomePageFragment? = null

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, KaiyuanActivity::class.java))
        }
    }
}