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

package com.eyepetizer.android.ui.community.follow

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lichengnan.BuildConfig
import com.example.lichengnan.GlobalUtil
import com.example.lichengnan.R
import com.example.lichengnan.constant.Const
import com.example.lichengnan.extension.*
import com.example.lichengnan.follow.FollowFragment
import com.example.lichengnan.logic.model.Follow
import com.example.lichengnan.ui.activity.NewDetailActivity
import com.example.lichengnan.ui.activity.comminute.ComminuteAdapter
import com.example.lichengnan.ui.activity.login.LoginActivity
import com.example.lichengnan.ui.distory.home.HomeAdapter
import com.example.lichengnan.ui.horder.EmptyViewHolder
import com.example.lichengnan.util.DateUtil
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer

class FollowAdapter(val fragment: FollowFragment, var dataList: List<Follow.Item>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = dataList.size + 1

    override fun getItemViewType(position: Int) = when {
        position == 0 -> Const.ItemViewType.CUSTOM_HEADER
        dataList[position - 1].type == "autoPlayFollowCard" && dataList[position - 1].data.dataType == "FollowCard" -> AUTO_PLAY_FOLLOW_CARD
        else -> Const.ItemViewType.UNKNOWN
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        Const.ItemViewType.CUSTOM_HEADER -> HeaderViewHolder(R.layout.item_community_follow_header_type.inflate(parent))
        AUTO_PLAY_FOLLOW_CARD -> AutoPlayFollowCardViewHolder(R.layout.item_community_auto_play_follow_card_follow_card_type.inflate(parent))
        else -> EmptyViewHolder(View(parent.context))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.itemView.setOnClickListener { LoginActivity.start(fragment.activity) }

            is AutoPlayFollowCardViewHolder -> {
                val item = dataList[position - 1]
                item.data.content.data.run {
                    holder.ivAvatar.load(item.data.header.icon ?: author?.icon ?: "")

                    holder.tvReleaseTime.text = DateUtil.getDate(releaseTime ?: author?.latestReleaseTime ?: System.currentTimeMillis(), "HH:mm")
                    holder.tvTitle.text = title
                    holder.tvNickname.text = author?.name ?: ""
                    holder.tvContent.text = description
                    holder.tvCollectionCount.text = consumption.collectionCount.toString()
                    holder.tvReplyCount.text = consumption.replyCount.toString()
                    holder.tvVideoDuration.visible()    //视频播放后，复用tvVideoDuration直接隐藏了
                    holder.tvVideoDuration.text = duration.conversionVideoDuration()
                    HomeAdapter.startAutoPlay(fragment.activity, holder.videoPlayer, position, playUrl, cover.feed, TAG, object : GSYSampleCallBack() {
                        override fun onPrepared(url: String?, vararg objects: Any?) {
                            super.onPrepared(url, *objects)
                            holder.tvVideoDuration.gone()
                            GSYVideoManager.instance().isNeedMute = true
                        }

                        override fun onClickResume(url: String?, vararg objects: Any?) {
                            super.onClickResume(url, *objects)
                            holder.tvVideoDuration.gone()
                        }

                        override fun onClickBlank(url: String?, vararg objects: Any?) {
                            super.onClickBlank(url, *objects)
                            holder.tvVideoDuration.visible()
                            NewDetailActivity.start(
                                fragment.activity,
                                NewDetailActivity.VideoInfo(id, playUrl, title, description, category, library, consumption, cover, author!!, webUrl)
                            )
                        }
                    })
                    holder.let {
                        setOnClickListener(it.videoPlayer.thumbImageView, it.itemView, it.ivCollectionCount, it.tvCollectionCount, it.ivFavorites, it.tvFavorites, it.ivShare)
                        {
                            when (this) {
                                it.videoPlayer.thumbImageView, it.itemView -> {
                                    NewDetailActivity.start(
                                        fragment.activity, NewDetailActivity.VideoInfo(
                                            item.data.content.data.id, playUrl, title, description, category, library, consumption, cover, author!!, webUrl
                                        )
                                    )
                                }
                                it.ivCollectionCount, it.tvCollectionCount, it.ivFavorites, it.tvFavorites -> {
                                    LoginActivity.start(fragment.activity)
                                }
                                it.ivShare -> {
                                    showDialogShare(fragment.activity, getShareContent(item))
                                }
                            }
                        }
                    }
                }
            }
            else -> {
                holder.itemView.gone()
                if (BuildConfig.DEBUG) "${TAG}:${Const.Toast.BIND_VIEWHOLDER_TYPE_WARN}\n${holder}".showToast()
            }
        }
    }

    private fun getShareContent(item: Follow.Item): String {
        item.data.content.data.run {
            val linkUrl = "${item.data.content.data.webUrl.raw}&utm_campaign=routine&utm_medium=share&utm_source=others&uid=0&resourceType=${resourceType}"
            return "${title}|${GlobalUtil.appName}：\n${linkUrl}"
        }
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class AutoPlayFollowCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivAvatar = view.findViewById<ImageView>(R.id.ivAvatar)
        val tvReleaseTime = view.findViewById<TextView>(R.id.tvReleaseTime)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvNickname = view.findViewById<TextView>(R.id.tvNickname)
        val tvContent = view.findViewById<TextView>(R.id.tvContent)
        val ivCollectionCount = view.findViewById<ImageView>(R.id.ivCollectionCount)
        val tvCollectionCount = view.findViewById<TextView>(R.id.tvCollectionCount)
        val ivReply = view.findViewById<ImageView>(R.id.ivReply)
        val tvReplyCount = view.findViewById<TextView>(R.id.tvReplyCount)
        val ivFavorites = view.findViewById<ImageView>(R.id.ivFavorites)
        val tvFavorites = view.findViewById<TextView>(R.id.tvFavorites)
        val tvVideoDuration = view.findViewById<TextView>(R.id.tvVideoDuration)
        val ivShare = view.findViewById<ImageView>(R.id.ivShare)
        val videoPlayer: GSYVideoPlayer = view.findViewById<GSYVideoPlayer>(R.id.videoPlayer)
    }

    companion object {
        const val TAG = "FollowAdapter"
        const val AUTO_PLAY_FOLLOW_CARD = Const.ItemViewType.MAX    //type:autoPlayFollowCard -> dataType:FollowCard
    }
}