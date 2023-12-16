package com.android.warmindoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.warmindoapp.data.AppDatabase
import com.android.warmindoapp.data.entity.Pengguna

class MainActivity : AppCompatActivity() {
  private lateinit var database: AppDatabase
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    database = AppDatabase.getInstance(applicationContext)

    // Contoh: Mengambil data dari tabel
    val penggunaList = database.penggunaDao().getAll()
  }
}