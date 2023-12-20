package com.android.warmindoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.warmindoapp.adapter.PenggunaAdapter
import com.android.warmindoapp.adapter.RoleAdapter
import com.android.warmindoapp.data.AppDatabase
import com.android.warmindoapp.data.entity.Pengguna
import com.android.warmindoapp.data.entity.PenggunaRole
import com.android.warmindoapp.data.entity.Role

class ManageUserActivity : AppCompatActivity() {
    private lateinit var  recyclerView: RecyclerView
    private lateinit var adapter: PenggunaAdapter
    private var list = mutableListOf<PenggunaRole>()
    private lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_user)

        database = AppDatabase.getInstance(applicationContext)

        list.addAll(database.penggunaDao().getRoleWithUsers())
//        adapter = PenggunaAdapter(list)
        Log.i("TESTLIST", list.toString())
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
//        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, RecyclerView.VERTICAL))
    }
}