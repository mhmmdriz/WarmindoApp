package com.android.warmindoapp

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.warmindoapp.data.AppDatabase
import com.android.warmindoapp.data.entity.Role
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.android.warmindoapp.adapter.RoleAdapter
import android.util.Log

class ManageRoleActivity : AppCompatActivity() {
    private lateinit var roleNameEditText: EditText
    private lateinit var addButton: Button
    private lateinit var roleListView: ListView

    private lateinit var roleList: MutableList<Role>
    private lateinit var roleAdapter: ArrayAdapter<String>
    private lateinit var  recyclerView: RecyclerView
    private lateinit var adapter: RoleAdapter
    private var list = mutableListOf<Role>()
    private lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_role)
        recyclerView = findViewById(R.id.recycler_view)

        database = AppDatabase.getInstance(applicationContext)
        adapter = RoleAdapter(list)
        list.forEach { Log.d("MyTag", it.toString()) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, VERTICAL))

    }

    private fun loadRoles() {
        // Load roles from the database and update the list
        val roleDao = AppDatabase.getInstance(this).roleDao()
        roleList.clear()
        roleList.addAll(roleDao.getAll())

        roleAdapter.notifyDataSetChanged()
    }

    fun onAddRoleClick(view: View) {
        val roleName = roleNameEditText.text.toString().trim()

        if (roleName.isNotEmpty()) {
            // Create a new Role object with the entered name
            val newRole = Role(role = roleName, status = "Aktif")

            // Insert the new role into the database
            val roleDao = AppDatabase.getInstance(this).roleDao()
            roleDao.insertAll(newRole)

            // Display a success message
            Toast.makeText(this, "Role added successfully", Toast.LENGTH_SHORT).show()

            // Clear the input field
            roleNameEditText.text.clear()

            // Reload roles after adding a new one
            loadRoles()
        } else {
            // Display an error message if the role name is empty
            Toast.makeText(this, "Please enter a role name", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getData(){
        list.clear()
        list.addAll(database.roleDao().getAll())
        adapter.notifyDataSetChanged()
    }
}