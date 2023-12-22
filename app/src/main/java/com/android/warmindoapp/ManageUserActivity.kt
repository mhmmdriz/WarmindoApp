package com.android.warmindoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.warmindoapp.adapter.PenggunaAdapter
import com.android.warmindoapp.adapter.RecyclerViewClickListener
import com.android.warmindoapp.adapter.RoleAdapter
import com.android.warmindoapp.data.AppDatabase
import com.android.warmindoapp.data.entity.Pengguna
import com.android.warmindoapp.data.entity.PenggunaRole
import com.android.warmindoapp.data.entity.Role

class ManageUserActivity : AppCompatActivity(), RecyclerViewClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PenggunaAdapter
    private var list = mutableListOf<PenggunaRole>()
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_user)

        database = AppDatabase.getInstance(applicationContext)

        list.addAll(database.penggunaDao().getRoleWithUsers())
        adapter = PenggunaAdapter(list)

        recyclerView = findViewById(R.id.recycler_view_user) // Menambahkan inisialisasi recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
//        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, RecyclerView.VERTICAL))
        adapter.setClickListener(this)
        Log.i("TESTLIST", list.toString())
    }

    private fun loadPengguna() {
        // Load roles from the database and update the list
        val penggunaDao = AppDatabase.getInstance(this).penggunaDao()
        list.clear()
        list.addAll(penggunaDao.getRoleWithUsers())
    }
    override fun onResume() {
        super.onResume()
        loadPengguna()
        adapter.notifyDataSetChanged()
    }

    override fun onEditClick(position: Int) {
        // Menghandle klik pada ImageView Edit pada posisi tertentu
        val intent = Intent(this, EditUserActivity::class.java)
        intent.putExtra("id", list[position].pengguna.idpengguna)
        startActivity(intent)
    }

    override fun onDeleteClick(position: Int) {
        // Menghandle klik pada ImageView Delete pada posisi tertentu
        database.penggunaDao().delete(list[position].pengguna)
        list.removeAt(position)
        adapter.notifyItemRemoved(position)
        showToast("User Telah dihapus")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun onClickAddUser(view: View) {
        val intent = Intent(this, AddUserActivity::class.java)
        startActivity(intent)
    }
}