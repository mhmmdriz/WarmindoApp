package com.android.warmindoapp

import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.warmindoapp.data.AppDatabase
import com.android.warmindoapp.data.entity.Role

class ManageRoleActivity : AppCompatActivity() {
    private lateinit var roleNameEditText: EditText
    private lateinit var addButton: Button
    private lateinit var roleListView: ListView

    private lateinit var roleList: MutableList<Role>
    private lateinit var roleAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_role)

        roleNameEditText = findViewById(R.id.editTextRoleName)
        addButton = findViewById(R.id.buttonAddRole)
        roleListView = findViewById(R.id.listViewRoles)

        roleList = mutableListOf()
        roleAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, roleList.map { it.role ?: "" })

        roleListView.adapter = roleAdapter

        loadRoles()

        addButton.setOnClickListener {
            onAddRoleClick(it)
        }

        roleListView.setOnItemClickListener { _, _, position, _ ->
            val selectedRole = roleList[position]
            // Implement your logic here when a role item is clicked
            // For example, you can show a dialog for update/delete options
        }
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
}