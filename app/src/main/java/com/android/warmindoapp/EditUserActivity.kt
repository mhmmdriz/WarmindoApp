package com.android.warmindoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.android.warmindoapp.data.AppDatabase
import com.android.warmindoapp.data.entity.Pengguna
import com.android.warmindoapp.data.entity.Role

class EditUserActivity : AppCompatActivity() {
  private lateinit var database: AppDatabase
  private lateinit var etNama: EditText
  private lateinit var etUsername: EditText
  private lateinit var etPassword: EditText
  private lateinit var spinStatus: Spinner
  private lateinit var spinRole: Spinner
  private lateinit var roleId : Array<Int?>
  private lateinit var statusItems : Array<String?>
  var selectedRoleId: Int? = null
  var selectedStatus: String? = null
  var pengguna: Pengguna? = null
  var existingUser: Pengguna? = null
  var usernameOld: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_edit_user)

    database = AppDatabase.getInstance(applicationContext)

    etNama = findViewById(R.id.et_nama)
    etUsername = findViewById(R.id.et_username)
    etPassword = findViewById(R.id.et_password)
    spinRole = findViewById(R.id.spinner)
    spinStatus = findViewById(R.id.spinner_status)

    // Inisialisasi Spinner dengan data dari database
    setupSpinner()

    val intent = intent.extras
    val id = intent?.getInt("id", 0) ?: 0
    if(intent != null){
      pengguna = database.penggunaDao().getById(id)

      etNama.setText(pengguna!!.namapengguna)
      etUsername.setText(pengguna!!.username)
      etPassword.setText(pengguna!!.password)
      spinStatus.setSelection(statusItems.indexOf(pengguna!!.status))
      spinRole.setSelection(roleId.indexOf(pengguna!!.idrole))

      selectedRoleId = pengguna!!.idrole
      selectedStatus = pengguna!!.status
      usernameOld = pengguna!!.username
    }



    val btnSave: Button = findViewById(R.id.btn_save)
    btnSave.setOnClickListener {
      // Panggil fungsi untuk melakukan insert ke tabel Pengguna
      // Mendapatkan data dari inputan pengguna
      val nama = etNama.text.toString()
      val username = etUsername.text.toString()
      val password = etPassword.text.toString()

      // Mendapatkan ID role yang dipilih dari Spinner
      selectedRoleId = roleId[spinRole.selectedItemPosition]
      selectedStatus = statusItems[spinStatus.selectedItemPosition]

      if (
        ValidationUtils.isTextNotEmpty(username) &&
        ValidationUtils.isTextNotEmpty(nama) &&
        ValidationUtils.isTextNotEmpty(password)
      ){
        if(username != usernameOld){
          existingUser = database.penggunaDao().getByUsername(username)
        }else{
          existingUser = null
        }

        if (existingUser == null) {
          if (intent != null) {
            database.penggunaDao().update(
              Pengguna(
                intent.getInt("id"),
                username,
                password,
                nama,
                selectedRoleId,
                selectedStatus,
                ""
              )
            )
          }
          finish()
          Toast.makeText(this, "User updated", Toast.LENGTH_SHORT).show()
        } else {
          Toast.makeText(this, "Username is already registered", Toast.LENGTH_SHORT).show()
        }
      }else{
        Toast.makeText(this, "Please input all field", Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun setupSpinner() {
    val roles = database.roleDao().getAll()
    val roleNames = roles.map { it.role }.toTypedArray()
    roleId = roles.map { it.idrole }.toTypedArray()

    statusItems = arrayOf("Aktif", "Tidak")
    val statusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, statusItems)
    spinStatus.adapter = statusAdapter

    // Mengatur adapter untuk Spinner
    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, roleNames)
    spinRole.adapter = adapter
  }

}