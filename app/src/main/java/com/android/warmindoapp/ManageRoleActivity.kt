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
    private lateinit var  recyclerView: RecyclerView
    private lateinit var adapter: RoleAdapter
    private var list = mutableListOf<Role>()
    private lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_role)
        recyclerView = findViewById(R.id.recycler_view)
        addButton = findViewById(R.id.buttonAddRole)
        roleNameEditText = findViewById(R.id.editTextRoleName)

        database = AppDatabase.getInstance(applicationContext)

        list.addAll(database.roleDao().getAll())
        adapter = RoleAdapter(list)
        Log.i("HALO", list.toString())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, VERTICAL))
        adapter.setDialog(object : RoleAdapter.Dialog{
            override fun onClick(position: Int) {
                // membuat dialog view
                val dialog = AlertDialog.Builder(this@ManageRoleActivity)
                dialog.setTitle(list[position].role)
                dialog.setItems(R.array.items_option, DialogInterface.OnClickListener{ dialog, which ->
                    if (which == 0){
                        // coding ubah
                        val intent = Intent(this@ManageRoleActivity, EditRoleActivity::class.java)
                        intent.putExtra("id", list[position].idrole)
                        startActivity(intent)
                    }else if(which == 1){
                        // coding hapus
                        database.roleDao().delete(list[position])
                        getData()
                    }else{
                        dialog.dismiss()
                    }
                })

                //menampilkan dialognya
                val dialogView = dialog.create()
                dialogView.show()
            }

        })
        addButton.setOnClickListener {
            onAddRoleClick(it)
        }
    }

    private fun loadRoles() {
        // Load roles from the database and update the list
        val roleDao = AppDatabase.getInstance(this).roleDao()
        list.clear()
        list.addAll(roleDao.getAll())
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

            // Notify the adapter that the dataset has changed
            adapter.notifyDataSetChanged()
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

    override fun onResume() {
        super.onResume()
        loadRoles()
        adapter.notifyDataSetChanged()
    }
}