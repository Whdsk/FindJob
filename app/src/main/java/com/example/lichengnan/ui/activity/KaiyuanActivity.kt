package com.example.lichengnan.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.lichengnan.BaseActivity
import com.example.lichengnan.GlobalUtil
import com.example.lichengnan.R
import com.example.lichengnan.event.MessageEvent
import com.example.lichengnan.event.SwitchPagesEvent
import com.example.lichengnan.extension.setOnClickListener
import com.example.lichengnan.ui.activity.comminute.CommunityFragment
import com.example.lichengnan.ui.distory.home.HomePageFragment
import com.example.lichengnan.util.DialogAppraiseTipsWorker
import com.example.lichengnan.util.logD
import org.greenrobot.eventbus.EventBus

/**

 * @author：gaohangbo on 2020/9/2 11:16

 */
class KaiyuanActivity: BaseActivity()  {

    private var backPressTime = 0L

    private var homePageFragment: HomePageFragment? = null

    private var communityFragment: CommunityFragment? = null

    private var notificationFragment: NotificationFragment? = null

    private var mineFragment: MineFragment? = null

    private val fragmentManager: FragmentManager by lazy { supportFragmentManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun setupViews() {
        observe()
        setOnClickListener(btnHomePage, btnCommunity, btnNotification, ivRelease, btnMine) {
            when (this) {
                btnHomePage -> {
                    notificationUiRefresh(0)
                    setTabSelection(0)
                }
                btnCommunity -> {
                    notificationUiRefresh(1)
                    setTabSelection(1)
                }
                btnNotification -> {
                    notificationUiRefresh(2)
                    setTabSelection(2)
                }
                ivRelease -> {
                    LoginActivity.start(this@MainActivity)
                }
                btnMine -> {
                    notificationUiRefresh(3)
                    setTabSelection(3)
                }
            }
        }
        setTabSelection(0)
    }

    override fun onMessageEvent(messageEvent: MessageEvent) {
        super.onMessageEvent(messageEvent)
        when {
            messageEvent is SwitchPagesEvent && CommendFragment::class.java == messageEvent.activityClass -> {
                btnCommunity.performClick()
            }
            else -> {
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            processBackPressed()
        }
    }

    private fun processBackPressed() {
        val now = System.currentTimeMillis()
        if (now - backPressTime > 2000) {
            String.format(GlobalUtil.getString(R.string.press_again_to_exit), GlobalUtil.appName).showToast()
            backPressTime = now
        } else {
            super.onBackPressed()
        }
    }

    private fun setTabSelection(index: Int) {
        clearAllSelected()
        fragmentManager.beginTransaction().apply {
            hideFragments(this)
            when (index) {
                0 -> {
                    ivHomePage.isSelected = true
                    tvHomePage.isSelected = true
                    if (homePageFragment == null) {
                        homePageFragment = HomePageFragment.newInstance()
                        add(R.id.homeActivityFragContainer, homePageFragment!!)
                    } else {
                        show(homePageFragment!!)
                    }
                }
                1 -> {
                    ivCommunity.isSelected = true
                    tvCommunity.isSelected = true
                    if (communityFragment == null) {
                        communityFragment = CommunityFragment()
                        add(R.id.homeActivityFragContainer, communityFragment!!)
                    } else {
                        show(communityFragment!!)
                    }
                }
                2 -> {
                    ivNotification.isSelected = true
                    tvNotification.isSelected = true
                    if (notificationFragment == null) {
                        notificationFragment = NotificationFragment()
                        add(R.id.homeActivityFragContainer, notificationFragment!!)
                    } else {
                        show(notificationFragment!!)
                    }
                }
                3 -> {
                    ivMine.isSelected = true
                    tvMine.isSelected = true
                    if (mineFragment == null) {
                        mineFragment = MineFragment.newInstance()
                        add(R.id.homeActivityFragContainer, mineFragment!!)
                    } else {
                        show(mineFragment!!)
                    }
                }
                else -> {
                    ivHomePage.isSelected = true
                    tvHomePage.isSelected = true
                    if (homePageFragment == null) {
                        homePageFragment = HomePageFragment.newInstance()
                        add(R.id.homeActivityFragContainer, homePageFragment!!)
                    } else {
                        show(homePageFragment!!)
                    }
                }
            }
        }.commitAllowingStateLoss()
    }

    private fun clearAllSelected() {
        ivHomePage.isSelected = false
        tvHomePage.isSelected = false
        ivCommunity.isSelected = false
        tvCommunity.isSelected = false
        ivNotification.isSelected = false
        tvNotification.isSelected = false
        ivMine.isSelected = false
        tvMine.isSelected = false
    }

    private fun hideFragments(transaction: FragmentTransaction) {
        transaction.run {
            if (homePageFragment != null) hide(homePageFragment!!)
            if (communityFragment != null) hide(communityFragment!!)
            if (notificationFragment != null) hide(notificationFragment!!)
            if (mineFragment != null) hide(mineFragment!!)
        }
    }

    private fun notificationUiRefresh(selectionIndex: Int) {
        when (selectionIndex) {
            0 -> {
                if (ivHomePage.isSelected) EventBus.getDefault().post(RefreshEvent(HomePageFragment::class.java))
            }
            1 -> {
                if (ivCommunity.isSelected) EventBus.getDefault().post(RefreshEvent(CommunityFragment::class.java))
            }
            2 -> {
                if (ivNotification.isSelected) EventBus.getDefault().post(RefreshEvent(NotificationFragment::class.java))
            }
            3 -> {
                if (ivMine.isSelected) EventBus.getDefault().post(RefreshEvent(MineFragment::class.java))
            }
        }
    }

    private fun observe() {
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(DialogAppraiseTipsWorker.showDialogWorkRequest.id).observe(this, Observer { workInfo ->
            logD(TAG, "observe: workInfo.state = ${workInfo.state}")
            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                WorkManager.getInstance(this).cancelAllWork()
            } else if (workInfo.state == WorkInfo.State.RUNNING) {
                if (isActive) {
                    DialogAppraiseTipsWorker.showDialog(this)
                    WorkManager.getInstance(this).cancelAllWork()
                }
            }
        })
    }

    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, KaiyuanActivity::class.java))
        }
    }
}