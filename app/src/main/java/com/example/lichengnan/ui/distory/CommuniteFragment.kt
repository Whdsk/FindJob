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

package com.example.lichengnan.ui.distory

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.lichengnan.GlobalUtil
import com.example.lichengnan.R
import com.example.lichengnan.event.MessageEvent
import com.example.lichengnan.event.RefreshEvent
import com.example.lichengnan.extension.dp2px
import com.example.lichengnan.extension.showToast
import com.example.lichengnan.ui.activity.BaseFragment
import com.example.lichengnan.ui.activity.comminute.ComminuteAdapter
import com.example.lichengnan.ui.distory.communite.CommuniteAdapter
import com.example.lichengnan.ui.distory.model.CommendViewModel
import com.example.lichengnan.util.InjectorUtil
import com.example.lichengnan.util.ResponseHandler
import com.scwang.smart.refresh.layout.constant.RefreshState
import kotlinx.android.synthetic.main.fragment_refresh_layout.*

/**
 * 社区-推荐列表界面。
 *
 * @author vipyinzhiwei
 * @since  2020/5/1
 */
class CommuniteFragment : BaseFragment() {

    /**
     * 列表左or右间距
     */
    val bothSideSpace = GlobalUtil.getDimension(R.dimen.listSpaceSize)

    /**
     * 列表中间内间距，左or右。
     */
    val middleSpace = dp2px(3f)

    /**
     * 通过获取屏幕宽度来计算出每张图片最大的宽度。
     *
     * @return 计算后得出的每张图片最大的宽度。
     */
    val maxImageWidth: Int
        get() {
            val windowManager = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val metrics = DisplayMetrics()
            windowManager.defaultDisplay?.getMetrics(metrics)
            val columnWidth = metrics.widthPixels
            return (columnWidth - ((bothSideSpace * 2) + (middleSpace * 2))) / 2
        }

    private val viewModel by lazy { ViewModelProvider(this, InjectorUtil.getCommunityCommendViewModelFactory()).get(CommendViewModel::class.java) }

    private lateinit var adapter: CommuniteAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater.inflate(R.layout.fragment_refresh_layout, container, false))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = CommuniteAdapter(this, viewModel.dataList)
        val mainLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mainLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager = mainLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(ComminuteAdapter.ItemDecoration(this))
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = null
        refreshLayout.setOnRefreshListener { viewModel.onRefresh() }
        refreshLayout.setOnLoadMoreListener { viewModel.onLoadMore() }
        observe()
    }

    override fun loadDataOnce() {
        super.loadDataOnce()
        startLoading()
    }

    override fun startLoading() {
        super.startLoading()
        viewModel.onRefresh()
    }

    @CallSuper
    override fun loadFailed(msg: String?) {
        super.loadFailed(msg)
        showLoadErrorView(msg ?: GlobalUtil.getString(R.string.unknown_error)) { startLoading() }
    }

    override fun onMessageEvent(messageEvent: MessageEvent) {
        super.onMessageEvent(messageEvent)
        if (messageEvent is RefreshEvent && javaClass == messageEvent.activityClass) {
            refreshLayout.autoRefresh()
            if (recyclerView.adapter?.itemCount ?: 0 > 0) recyclerView.scrollToPosition(0)
        }
    }

    private fun observe() {
        viewModel.dataListLiveData.observe(viewLifecycleOwner, Observer { result ->
            val response = result.getOrNull()
            if (response == null) {
                ResponseHandler.getFailureTips(result.exceptionOrNull()).let { if (viewModel.dataList.isNullOrEmpty()) loadFailed(it) else it.showToast() }
                refreshLayout.closeHeaderOrFooter()
                return@Observer
            }
            loadFinished()
            viewModel.nextPageUrl = response.nextPageUrl
            if (response.itemList.isNullOrEmpty() && viewModel.dataList.isEmpty()) {
                refreshLayout.closeHeaderOrFooter()
                return@Observer
            }
            if (response.itemList.isNullOrEmpty() && viewModel.dataList.isNotEmpty()) {
                refreshLayout.finishLoadMoreWithNoMoreData()
                return@Observer
            }
            when (refreshLayout.state) {
                RefreshState.None, RefreshState.Refreshing -> {
                    viewModel.dataList.clear()
                    viewModel.dataList.addAll(response.itemList)
                    adapter.notifyDataSetChanged()
                }
                RefreshState.Loading -> {
                    val itemCount = viewModel.dataList.size
                    viewModel.dataList.addAll(response.itemList)
                    adapter.notifyItemRangeInserted(itemCount, response.itemList.size)
                }
                else -> {
                }
            }
            if (response.nextPageUrl.isNullOrEmpty()) {
                refreshLayout.finishLoadMoreWithNoMoreData()
            } else {
                refreshLayout.closeHeaderOrFooter()
            }
        })
    }

    companion object {

        fun newInstance() = CommuniteFragment()
    }

}