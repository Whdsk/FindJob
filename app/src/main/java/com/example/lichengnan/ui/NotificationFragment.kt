/*
 * Copyright (c) 2020. vipyinzhiwei <vipyinzhiwei@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.lichengnan.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lichengnan.GlobalUtil
import com.example.lichengnan.R
import com.example.lichengnan.event.MessageEvent
import com.example.lichengnan.event.RefreshEvent
import com.example.lichengnan.event.SwitchPagesEvent
import com.example.lichengnan.ui.activity.inbox.InboxFragment
import com.example.lichengnan.ui.fragment.PushFragment
import com.eyepetizer.android.logic.model.TabEntity
import com.flyco.tablayout.listener.CustomTabEntity
import org.greenrobot.eventbus.EventBus

/**
 * 通知主界面。
 *
 * @author vipyinzhiwei
 * @since  2020/5/1
 */
class NotificationFragment : BaseViewPagerFragment() {

    override val createTitles = ArrayList<CustomTabEntity>().apply {
        add(TabEntity(GlobalUtil.getString(R.string.push)))
        add(TabEntity(GlobalUtil.getString(R.string.interaction)))
        add(TabEntity(GlobalUtil.getString(R.string.inbox)))
    }

    override val createFragments: Array<Fragment> = arrayOf(PushFragment.newInstance(), InteractionFragment.newInstance(), InboxFragment.newInstance())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater.inflate(R.layout.fragment_main_container, container, false))
    }

    override fun onMessageEvent(messageEvent: MessageEvent) {
        super.onMessageEvent(messageEvent)
        if (messageEvent is RefreshEvent && this::class.java == messageEvent.activityClass) {
            when (viewPager?.currentItem) {
                0 -> EventBus.getDefault().post(RefreshEvent(PushFragment::class.java))
                1 -> EventBus.getDefault().post(RefreshEvent(InteractionFragment::class.java))
                2 -> EventBus.getDefault().post(RefreshEvent(InboxFragment::class.java))
                else -> {
                }
            }
        } else if (messageEvent is SwitchPagesEvent) {
            when (messageEvent.activityClass) {
                PushFragment::class.java -> viewPager?.currentItem = 0
                InteractionFragment::class.java -> viewPager?.currentItem = 1
                InboxFragment::class.java -> viewPager?.currentItem = 2
                else -> {
                }
            }
        }
    }

    companion object {

        fun newInstance() = NotificationFragment()
    }
}