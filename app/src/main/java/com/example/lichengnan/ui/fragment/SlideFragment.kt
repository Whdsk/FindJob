package com.example.lichengnan.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.lichengnan.R
import com.example.lichengnan.bean.SlideBean
import com.example.lichengnan.ui.activity.BaseFragment
import com.example.lichengnan.ui.widget.SmileView
import com.example.lichengnan.widget.slide.ItemConfig
import com.example.lichengnan.widget.slide.ItemTouchHelperCallback
import com.example.lichengnan.widget.slide.OnSlideListener
import com.example.lichengnan.widget.slide.SlideLayoutManager
import java.util.*

/**
 * Created by 钉某人
 * github: https://github.com/DingMouRen
 * email: naildingmouren@gmail.com
 */
class SlideFragment : BaseFragment() {
     lateinit var mRecyclerView: RecyclerView
    lateinit var mSmileView: SmileView
    private var mSlideLayoutManager: SlideLayoutManager? = null
    private var mItemTouchHelper: ItemTouchHelper? = null
    lateinit var mItemTouchHelperCallback: ItemTouchHelperCallback<*>
    private var mAdapter: MyAdapter? = null
     val mList: MutableList<SlideBean?> = ArrayList()
    private var mLikeCount = 50
    private var mDislikeCount = 50
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_slide, container, false)
        initView(rootView)
        initListener()
        return rootView
    }

    private fun initView(rootView: View) {
        mRecyclerView = rootView.findViewById(R.id.recycler_view)
        mSmileView = rootView.findViewById(R.id.smile_view)
        mSmileView.setLike(mLikeCount)
        mSmileView.setDisLike(mDislikeCount)
        mAdapter = MyAdapter()
        mRecyclerView.adapter = mAdapter
        addData()
        mItemTouchHelperCallback = ItemTouchHelperCallback<Any?>(mRecyclerView.getAdapter()!!, mList as List<Any?>)
        mItemTouchHelper = ItemTouchHelper(mItemTouchHelperCallback)
        mSlideLayoutManager = SlideLayoutManager(mRecyclerView, mItemTouchHelper!!)
        mItemTouchHelper!!.attachToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = mSlideLayoutManager
    }

//    class MySlideListener:OnSlideListener{
//        override fun onSliding(viewHolder: RecyclerView.ViewHolder?, ratio: Float, direction: Int) {
//             if (direction == ItemConfig.SLIDING_LEFT) {
//                }
//                else if (direction == ItemConfig.SLIDING_RIGHT) {
//                }
//        }
//
//        override fun onSlided(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
//                            if (direction == ItemConfig.SLIDED_LEFT) {
//                    mDislikeCount--
//                    mSmileView.setDisLike(mDislikeCount)
//                    mSmileView.disLikeAnimation()
//                } else if (direction == ItemConfig.SLIDED_RIGHT) {
//                    mLikeCount++
//                    mSmileView.setLike(mLikeCount)
//                    mSmileView.likeAnimation()
//                }
//                val position = viewHolder.adapterPosition
//        }
//
//        override fun onClear() {
//            addData()
//        }
//
//    }
    private fun initListener() {
//        val mySlideListener = MySlideListener()
//        mItemTouchHelperCallback.setOnSlideListener(mySlideListener)
//        mItemTouchHelperCallback.setOnSlideListener(OnSlideListener)
        mItemTouchHelperCallback.setOnSlideListener(object : OnSlideListener {
            override fun onSliding(viewHolder: RecyclerView.ViewHolder?, ratio: Float, direction: Int) {
                if (direction == ItemConfig.SLIDING_LEFT) {
                } else if (direction == ItemConfig.SLIDING_RIGHT) {
                }
            }

            override fun onSlided(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                if (direction == ItemConfig.SLIDED_LEFT) {
                    mDislikeCount--
                    mSmileView.setDisLike(mDislikeCount)
                    mSmileView.disLikeAnimation()
                } else if (direction == ItemConfig.SLIDED_RIGHT) {
                    mLikeCount++
                    mSmileView.setLike(mLikeCount)
                    mSmileView.likeAnimation()
                }
                val position = viewHolder?.adapterPosition
                Log.e(SlideFragment.TAG, "onSlided--position:$position")
            }

            override fun onClear() {
                addData()
            }
        })
    }

    /**
     * 向集合中添加数据
     */
    private fun addData() {
        val icons = intArrayOf(R.mipmap.header_icon_1, R.mipmap.header_icon_2, R.mipmap.header_icon_3,
                R.mipmap.header_icon_4, R.mipmap.header_icon_1, R.mipmap.header_icon_2)
        val titles = arrayOf("Acknowledging", "Belief", "Confidence", "Dreaming", "Happiness", "Confidence")
        val says = arrayOf(
                "Do one thing at a time, and do well.",
                "Keep on going never give up.",
                "Whatever is worth doing is worth doing well.",
                "I can because i think i can.",
                "Jack of all trades and master of none.",
                "Keep on going never give up.",
                "Whatever is worth doing is worth doing well.")
        val bgs = intArrayOf(
                R.mipmap.img_slide_1,
                R.mipmap.img_slide_2,
                R.mipmap.img_slide_3,
                R.mipmap.img_slide_4,
                R.mipmap.img_slide_5,
                R.mipmap.img_slide_6
        )
        for (i in 0..5) {
            mList.add(SlideBean(bgs[i], titles[i], icons[i], says[i]))
        }
    }

    /**
     * 适配器
     */
    internal inner class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(activity).inflate(R.layout.item_slide, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val bean = mList[position]
            holder.imgBg.setImageResource(bean!!.itemBg)
            holder.tvTitle.text = bean.title
            holder.userIcon.setImageResource(bean.userIcon)
            holder.userSay.text = bean.userSay
        }

        override fun getItemCount(): Int {
            return mList.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var imgBg: ImageView
            var userIcon: ImageView
            var tvTitle: TextView
            var userSay: TextView

            init {
                imgBg = itemView.findViewById(R.id.img_bg)
                userIcon = itemView.findViewById(R.id.img_user)
                tvTitle = itemView.findViewById(R.id.tv_title)
                userSay = itemView.findViewById(R.id.tv_user_say)
            }
        }
    }

    companion object {
        private const val TAG = "SlideFragment"
    }
}
