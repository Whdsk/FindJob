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

package com.example.lichengnan.ui.push

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.lichengnan.network.api.MainPageService
import com.example.lichengnan.ui.distory.repository.MainPageRepository
import com.eyepetizer.android.logic.model.PushMessage

class PushViewModel(val repository: MainPageRepository) : ViewModel() {

    var dataList = ArrayList<PushMessage.Message>()

    private var requestParamLiveData = MutableLiveData<String>()

    var nextPageUrl: String? = null

    val dataListLiveData = Transformations.switchMap(requestParamLiveData) { url ->
        liveData {
            val resutlt = try {
                val pushMessage = repository.refreshPushMessage(url)
                Result.success(pushMessage)
            } catch (e: Exception) {
                Result.failure<PushMessage>(e)
            }
            emit(resutlt)
        }
    }

    fun onRefresh() {
        requestParamLiveData.value = MainPageService.PUSHMESSAGE_URL
    }

    fun onLoadMore() {
        requestParamLiveData.value = nextPageUrl ?: ""
    }
}
