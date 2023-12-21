package com.android.warmindoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.warmindoapp.adapter.TransaksiAdapter
import com.android.warmindoapp.data.AppDatabase
import com.android.warmindoapp.data.entity.Transaksi

class ManageTransaksiActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransaksiAdapter
    private var list = mutableListOf<Transaksi>()
    private lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_transaksi)
        recyclerView = findViewById(R.id.rv_transaksi)

        database = AppDatabase.getInstance(applicationContext)

        list.addAll(database.transaksiDao().getAll())
        adapter = TransaksiAdapter(list)
        list.forEach { Log.d("MyTag", it.toString()) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
//        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, RecyclerView.VERTICAL))
    }
}