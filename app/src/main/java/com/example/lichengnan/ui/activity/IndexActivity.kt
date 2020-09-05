package com.example.lichengnan.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lichengnan.ItemListDto
import com.example.lichengnan.ListAdapter
import com.example.lichengnan.R
import java.util.ArrayList

class IndexActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var listDto: MutableList<ItemListDto>
    val listAdapter by lazy { ListAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycleView)
        listDto=ArrayList()
        for (index in 1..10){
            if(index==0){
                listDto.add(ItemListDto("开源视频"))
            }else if(index==1){
                listDto.add(ItemListDto("开源视频"))
            }
            listDto.add(ItemListDto("视频"))
        }
        recyclerView.adapter=listAdapter
        listAdapter.data=listDto
        recyclerView.layoutManager = LinearLayoutManager(this)
        //listAdapter.setOnItemClickListener(adapter, view, position) ->{})
        listAdapter.setOnItemClickListener { adapter, view, position ->
            Toast.makeText(this@IndexActivity,"dddd",Toast.LENGTH_LONG).show()
            if(position==0){
                KaiyuanActivity.start(this@IndexActivity)
            }else if(position==1){
                PdfReaderActivity.start(this@IndexActivity)
            }
        }
    }
    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, IndexActivity::class.java))
        }
    }
}