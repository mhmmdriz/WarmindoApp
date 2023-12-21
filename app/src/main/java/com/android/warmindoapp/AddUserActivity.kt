package com.android.warmindoapp

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.warmindoapp.data.AppDatabase
import com.android.warmindoapp.data.entity.Pengguna
import com.android.warmindoapp.data.entity.Role

class AddUserActivity : AppCompatActivity() {
  private lateinit var database: AppDatabase
  private lateinit var etNama: EditText
  private lateinit var etUsername: EditText
  private lateinit var etStatus: EditText
  private lateinit var spinner: Spinner
  private lateinit var roleId : Array<Int?>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_user)

    database = AppDatabase.getInstance(applicationContext)

    etNama = findViewById(R.id.et_nama)
    etUsername = findViewById(R.id.et_username)
    spinner = findViewById(R.id.spinner)

    // Inisialisasi Spinner dengan data dari database
    setupSpinner()

    val btnSave: Button = findViewById(R.id.btn_save)
    btnSave.setOnClickListener {
      // Panggil fungsi untuk melakukan insert ke tabel Pengguna
      insertUser()
    }
  }

  private fun setupSpinner() {
    val roles = database.roleDao().getAll()
    val roleNames = roles.map { it.role }.toTypedArray()
    roleId = roles.map { it.idrole }.toTypedArray()

    // Mengatur adapter untuk Spinner
    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, roleNames)
    spinner.adapter = adapter
  }

  private fun insertUser() {
    // Mendapatkan data dari inputan pengguna
    val nama = etNama.text.toString()
    val username = etUsername.text.toString()

    // Mendapatkan ID role yang dipilih dari Spinner
    val selectedRoleId = roleId[spinner.selectedItemPosition]

    if (
      ValidationUtils.isTextNotEmpty(username) &&
      ValidationUtils.isTextNotEmpty(nama)
    ){
      val existingUser: Pengguna? = database.penggunaDao().getByUsername(username)
      if (existingUser == null) {
        // Membuat objek User
        val pengguna = Pengguna(namapengguna = nama, username = username, status="Aktif", idrole = selectedRoleId, foto="", password = "password")
        // Memasukkan data ke dalam database
        database.penggunaDao().insertAll(pengguna)
        finish()
        Toast.makeText(this, "User registered", Toast.LENGTH_SHORT).show()
      } else {
        Toast.makeText(this, "Username is already registered", Toast.LENGTH_SHORT).show()
      }
    }else{
      Toast.makeText(this, "Please input all field", Toast.LENGTH_SHORT).show()
    }


  }

}
