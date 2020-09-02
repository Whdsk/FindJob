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

package com.example.lichengnan.util

import android.app.Activity
import com.example.lichengnan.GlobalUtil
import com.example.lichengnan.R
import com.example.lichengnan.WebViewActivity
import com.example.lichengnan.constant.Const
import com.example.lichengnan.constant.Const.ActionUrl.*
import com.example.lichengnan.event.RefreshEvent
import com.example.lichengnan.event.SwitchPagesEvent
import com.example.lichengnan.extension.showToast
import com.example.lichengnan.ui.activity.BaseFragment
import com.example.lichengnan.ui.activity.DailyFragment
import com.example.lichengnan.ui.activity.NewDetailActivity
import com.example.lichengnan.ui.fragment.PushFragment
import org.greenrobot.eventbus.EventBus
import java.net.URLDecoder

/**
 * actionUrl事件处理工具类。通过截取actionUrl相关信息，并进行相应事件处理。
 *
 * @author vipyinzhiwei
 * @since  2020/6/14
 */
//用object 修饰的类为静态类，里面的方法和变量都为静态的。
object ActionUrlUtil {

    /**
     * 处理ActionUrl事件。
     *
     * @param fragment 上下文环境
     * @param actionUrl 待处理的url
     * @param toastTitle toast提示标题 or 没有匹配的事件需要处理，给出的提示标题。
     */
    fun process(fragment: BaseFragment, actionUrl: String?, toastTitle: String = "") {
        process(fragment.activity, actionUrl, toastTitle)
    }

    /**
     * 处理ActionUrl事件。
     *
     * @param activity 上下文环境
     * @param actionUrl 待处理的url
     * @param toastTitle toast提示标题 or 没有匹配的事件需要处理，给出的提示标题。
     */
    fun process(activity: Activity, actionUrl: String?, toastTitle: String = "") {
        if (actionUrl == null) return
        val decodeUrl = URLDecoder.decode(actionUrl, "UTF-8")
        when {
            decodeUrl.startsWith(Const.ActionUrl.WEBVIEW) -> {
                WebViewActivity.start(activity, decodeUrl.getWebViewInfo().first(), decodeUrl.getWebViewInfo().last())
            }
            decodeUrl == Const.ActionUrl.RANKLIST -> {
                "${toastTitle},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast()
            }
            decodeUrl.startsWith(Const.ActionUrl.TAG) -> {
                "${toastTitle},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast()
            }
            decodeUrl == Const.ActionUrl.HP_SEL_TAB_TWO_NEWTAB_MINUS_THREE -> {
                EventBus.getDefault().post(SwitchPagesEvent(DailyFragment::class.java))
            }
            decodeUrl == Const.ActionUrl.CM_TAGSQUARE_TAB_ZERO -> {
                "${toastTitle},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast()
            }
            decodeUrl == Const.ActionUrl.CM_TOPIC_SQUARE -> {
                "${toastTitle},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast()
            }
            decodeUrl == Const.ActionUrl.CM_TOPIC_SQUARE_TAB_ZERO -> {
                "${toastTitle},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast()
            }
            decodeUrl.startsWith(Const.ActionUrl.COMMON_TITLE) -> {
                "${toastTitle},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast()
            }
            actionUrl == Const.ActionUrl.HP_NOTIFI_TAB_ZERO -> {
                EventBus.getDefault().post(SwitchPagesEvent(PushFragment::class.java))
                EventBus.getDefault().post(RefreshEvent(PushFragment::class.java))
            }
            actionUrl.startsWith(Const.ActionUrl.TOPIC_DETAIL) -> {
                "${toastTitle},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast()
            }
            actionUrl.startsWith(Const.ActionUrl.DETAIL) -> {
                getConversionVideoId(actionUrl)?.run { NewDetailActivity.start(activity, this) }
            }
            else -> {
                "${toastTitle},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast()
            }
        }
    }

    /**
     * 截取标题与url信息。
     *
     * @return first=标题 last=url
     */
    private fun String.getWebViewInfo(): Array<String> {
        val title = this.split("title=").last().split("&url").first()
        val url = this.split("url=").last()
        return arrayOf(title, url)
    }

    /**
     *  截取视频id。
     *
     *  @param actionUrl 解码后的actionUrl
     *  @return 视频id
     */
    private fun getConversionVideoId(actionUrl: String?): Long? {
        return try {
            val list = actionUrl?.split(Const.ActionUrl.DETAIL)
            list!![1].toLong()
        } catch (e: Exception) {
            null
        }
    }
}