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

import com.example.lichengnan.follow.FollowViewModelFactory
import com.example.lichengnan.ui.activity.VideoRepository
import com.example.lichengnan.ui.activity.newDetail.NewDetailViewModelFactory
import com.example.lichengnan.ui.activity.search.SearchViewModelFactory
import com.example.lichengnan.ui.daily.DailyViewModelFactory
import com.example.lichengnan.ui.distory.communite.CommuniteViewModelFactory
import com.example.lichengnan.ui.distory.dao.EyepetizerDatabase
import com.example.lichengnan.ui.distory.home.HomeViewModelFactory
import com.example.lichengnan.ui.distory.repository.MainPageRepository
import com.example.lichengnan.ui.push.PushViewModelFactory
import com.eyepetizer.android.logic.network.EyepetizerNetwork
import com.eyepetizer.android.ui.home.discovery.DiscoveryViewModelFactory


/**
 * 应用程序逻辑控制管理类。
 *
 * @author vipyinzhiwei
 * @since  2020/5/2
 */
object InjectorUtil {

    private fun getMainPageRepository() = MainPageRepository.getInstance(EyepetizerDatabase.getMainPageDao(), EyepetizerNetwork.getInstance())

    private fun getViedoRepository() = VideoRepository.getInstance(EyepetizerDatabase.getVideoDao(), EyepetizerNetwork.getInstance())

    fun getDiscoveryViewModelFactory() = DiscoveryViewModelFactory(getMainPageRepository())

    fun getHomePageCommendViewModelFactory() = HomeViewModelFactory(getMainPageRepository())

    fun getDailyViewModelFactory() = DailyViewModelFactory(getMainPageRepository())

    fun getCommunityCommendViewModelFactory() = CommuniteViewModelFactory(getMainPageRepository())

    fun getFollowViewModelFactory() = FollowViewModelFactory(getMainPageRepository())

    fun getPushViewModelFactory() = PushViewModelFactory(getMainPageRepository())

    fun getSearchViewModelFactory() = SearchViewModelFactory(getMainPageRepository())

    fun getNewDetailViewModelFactory() = NewDetailViewModelFactory(getViedoRepository())

}