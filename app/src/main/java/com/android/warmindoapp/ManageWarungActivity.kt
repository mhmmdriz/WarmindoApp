package com.android.warmindoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.warmindoapp.adapter.WarungAdapter
import com.android.warmindoapp.data.AppDatabase
import com.android.warmindoapp.data.entity.Warung

class ManageWarungActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WarungAdapter
    private var list = mutableListOf<Warung>()
    private lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_warung)
        recyclerView = findViewById(R.id.rv_warung)

        database = AppDatabase.getInstance(applicationContext)

        list.addAll(database.warungDao().getAll())
        adapter = WarungAdapter(list)
        list.forEach { Log.d("MyTag", it.toString()) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
//        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, RecyclerView.VERTICAL))
    }
}