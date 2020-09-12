package com.example.lichengnan.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lichengnan.R
import com.example.lichengnan.application.EyepetizerApplication
import com.example.lichengnan.ui.activity.BaseFragment
import com.example.lichengnan.widget.picker.PickerLayoutManager
import java.util.*

/**
 * Created by 钉某人
 * github: https://github.com/DingMouRen
 * email: naildingmouren@gmail.com
 */
class PickerFragment : BaseFragment() {
     lateinit var mRecyclerView1: RecyclerView
     lateinit var mRecyclerView2: RecyclerView
     lateinit var mTvHour: TextView
     lateinit var mTvMinute: TextView
     lateinit var mPickerLayoutManager1: PickerLayoutManager
     lateinit var mPickerLayoutManager2: PickerLayoutManager

    companion object {
        private const val TAG = "PickerFragment"
        private val mHours: MutableList<String> = ArrayList()
        private val mMinutes: MutableList<String> = ArrayList()

        init {
            for (i in 1..24) {
                if (i <= 9) {
                    mHours.add("0$i")
                } else {
                    mHours.add(i.toString() + "")
                }
            }
            for (i in 0..59) {
                if (i <= 9) {
                    mMinutes.add("0$i")
                } else {
                    mMinutes.add(i.toString() + "")
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_picker, container, false)
        initView(rootView)
        initListener()
        return rootView
    }

    private fun initView(rootView: View) {
        mRecyclerView1 = rootView.findViewById(R.id.recycler1)
        mRecyclerView2 = rootView.findViewById(R.id.recycler2)
        mTvHour = rootView.findViewById(R.id.tv_hour)
        mTvMinute = rootView.findViewById(R.id.tv_minute)
        mPickerLayoutManager1 = PickerLayoutManager(context, mRecyclerView1, PickerLayoutManager.VERTICAL, false, 3, 0.4f, true)
        mRecyclerView1.setLayoutManager(mPickerLayoutManager1)
        mRecyclerView1.setAdapter(MyAdapter(mHours))
        mPickerLayoutManager2 = PickerLayoutManager(context, mRecyclerView2, PickerLayoutManager.VERTICAL, false, 3, 0.4f, true)
        mRecyclerView2.setLayoutManager(mPickerLayoutManager2)
        mRecyclerView2.setAdapter(MyAdapter(mMinutes))
    }

    private fun initListener() {
        mPickerLayoutManager1!!.setOnSelectedViewListener { view, position ->
            val textView = view as TextView
            Log.e(Companion.TAG, "layoutmanager1--" + textView.text)
            if (textView != null) mTvHour!!.text = textView.text
        }
        mPickerLayoutManager2!!.setOnSelectedViewListener { view, position ->
            val textView = view as TextView
            Log.e(Companion.TAG, "layoutmanager2--" + textView.text)
            if (textView != null) mTvMinute!!.text = textView.text
        }
    }

    internal inner class MyAdapter(private val mList: List<String>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
        private val mColors = intArrayOf(Color.YELLOW, Color.RED)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: View = LayoutInflater.from(EyepetizerApplication.context).inflate(R.layout.item_picker, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tvText.text = mList[position]
        }

        override fun getItemCount(): Int {
            return mList.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvText: TextView

            init {
                tvText = itemView.findViewById(R.id.tv_text)
            }
        }
    }


}