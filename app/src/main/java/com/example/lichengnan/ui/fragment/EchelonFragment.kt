package com.example.lichengnan.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lichengnan.R
import com.example.lichengnan.ui.activity.BaseFragment
import com.example.lichengnan.ui.widget.echelon.EchelonLayoutManager

/**
 * Created by 钉某人
 * github: https://github.com/DingMouRen
 * email: naildingmouren@gmail.com
 * 梯形布局
 */
class EchelonFragment : BaseFragment() {
    private var mRecyclerView: RecyclerView? = null
    private var mLayoutManager: EchelonLayoutManager? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_echelon, container, false)
        mRecyclerView = rootView.findViewById(R.id.recycler_view)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    private fun initData() {
        mLayoutManager = EchelonLayoutManager(context)
        mRecyclerView!!.layoutManager = mLayoutManager
        mRecyclerView!!.adapter = MyAdapter()
    }

    internal inner class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
        private val icons = intArrayOf(R.mipmap.header_icon_1, R.mipmap.header_icon_2, R.mipmap.header_icon_3, R.mipmap.header_icon_4)
        private val bgs = intArrayOf(R.mipmap.bg_1, R.mipmap.bg_2, R.mipmap.bg_3, R.mipmap.bg_4)
        private val nickNames = arrayOf("左耳近心", "凉雨初夏", "稚久九栀", "半窗疏影")
        private val descs = arrayOf(
                "回不去的地方叫故乡 没有根的迁徙叫流浪...",
                "人生就像迷宫，我们用上半生找寻入口，用下半生找寻出口",
                "原来地久天长，只是误会一场",
                "不是故事的结局不够好，而是我们对故事的要求过多",
                "只想优雅转身，不料华丽撞墙"
        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_echelon, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.icon.setImageResource(icons[position % 4])
            holder.nickName.text = nickNames[position % 4]
            holder.desc.text = descs[position % 5]
            holder.bg.setImageResource(bgs[position % 4])
        }

        override fun getItemCount(): Int {
            return 60
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var icon: ImageView
            var bg: ImageView
            var nickName: TextView
            var desc: TextView

            init {
                icon = itemView.findViewById(R.id.img_icon)
                bg = itemView.findViewById(R.id.img_bg)
                nickName = itemView.findViewById(R.id.tv_nickname)
                desc = itemView.findViewById(R.id.tv_desc)
            }
        }
    }
}