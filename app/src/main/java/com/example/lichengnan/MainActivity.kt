package com.example.lichengnan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var listDto: MutableList<ItemListDto>
    val listAdapter by lazy { ListAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycleView)
        listDto=ArrayList()
        for (index in 1..10){
            listDto.add(ItemListDto("视频"))
        }
        recyclerView.adapter=listAdapter
        listAdapter.data=listDto
        recyclerView.layoutManager = LinearLayoutManager(this)
        //listAdapter.setOnItemClickListener(adapter, view, position) ->{})
        listAdapter.setOnItemClickListener { adapter, view, position ->
            Toast.makeText(this@MainActivity,"dddd",Toast.LENGTH_LONG).show()
        }
    }
    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}