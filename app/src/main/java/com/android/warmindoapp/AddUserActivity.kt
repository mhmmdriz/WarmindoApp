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
  private lateinit var etTahunMasuk: EditText
  private lateinit var spinner: Spinner
  private lateinit var spinBulan: Spinner
  private lateinit var spinWarung: Spinner
  private lateinit var roleId : Array<Int?>
  private lateinit var warungId : Array<String?>
  private lateinit var bulanItems : Array<String?>
  private lateinit var bulanNames : Array<String?>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_user)

    database = AppDatabase.getInstance(applicationContext)

    etNama = findViewById(R.id.et_nama)
    etUsername = findViewById(R.id.et_username)
    etTahunMasuk = findViewById(R.id.et_tahunmasuk)
    spinner = findViewById(R.id.spinner)
    spinWarung = findViewById(R.id.spinner_warung)
    spinBulan = findViewById(R.id.spinner_bulan)

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

    val warungs = database.warungDao().getAll()
    val warungNames = warungs.map { it.namawarung }.toTypedArray()
    warungId = warungs.map { it.idwarung }.toTypedArray()

    // Mengatur adapter untuk Spinner
    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, roleNames)
    spinner.adapter = adapter

    val adapterWarung = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, warungNames)
    spinWarung.adapter = adapterWarung

    bulanItems = arrayOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
    bulanNames = arrayOf(
      "Januari", "Februari", "Maret", "April", "Mei", "Juni",
      "Juli", "Agustus", "September", "Oktober", "November", "Desember"
    )

    val bulanAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bulanNames)
    spinBulan.adapter = bulanAdapter
  }

  private fun insertUser() {
    // Mendapatkan data dari inputan pengguna
    val nama = etNama.text.toString()
    val username = etUsername.text.toString()
    val tahun = etTahunMasuk.text.toString()

    // Mendapatkan ID role yang dipilih dari Spinner
    val selectedRoleId = roleId[spinner.selectedItemPosition]
    val selectedWarungId = warungId[spinner.selectedItemPosition]
    val bulan = bulanItems[spinBulan.selectedItemPosition]

    if (
      ValidationUtils.isTextNotEmpty(username) &&
      ValidationUtils.isTextNotEmpty(nama)
    ){
      val existingUser: Pengguna? = database.penggunaDao().getByUsername(username)
      if (existingUser == null) {
        // Membuat objek User

        var maximumIdPengguna = database.penggunaDao().getNomorKaryawanTerbesar(tahun + bulan)?.toInt() ?: 0

        val incrementIdPengguna = (maximumIdPengguna + 1).toString()

        val idpengguna = selectedWarungId + tahun + bulan + "X" + incrementIdPengguna

        val pengguna = Pengguna(idpengguna=idpengguna, namapengguna = nama, username = username, status="Aktif", idrole = selectedRoleId, foto="", password = "password")

        // Memasukkan data ke dalam database
        val idList = database.penggunaDao().insert(pengguna)

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
