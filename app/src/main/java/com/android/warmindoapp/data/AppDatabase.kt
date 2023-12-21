// Membuat database
package com.android.warmindoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.warmindoapp.data.dao.MejaDao
import com.android.warmindoapp.data.dao.PenggunaDao
import com.android.warmindoapp.data.entity.Pengguna
import com.android.warmindoapp.data.dao.RoleDao
import com.android.warmindoapp.data.dao.TransaksiDao
import com.android.warmindoapp.data.dao.WarungDao
import com.android.warmindoapp.data.entity.Meja
import com.android.warmindoapp.data.entity.Role
import com.android.warmindoapp.data.entity.Transaksi
import com.android.warmindoapp.data.entity.Warung

@Database(entities = [Pengguna::class, Role::class, Warung::class, Meja::class, Transaksi::class], version = 5)

abstract class AppDatabase : RoomDatabase() {
  abstract fun penggunaDao(): PenggunaDao
  abstract fun roleDao(): RoleDao // Add this line for RoleDao
  abstract fun warungDao(): WarungDao
  abstract fun mejaDao(): MejaDao
  abstract fun transaksiDao(): TransaksiDao

  companion object{
    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase{
      if (instance == null){
        instance = Room.databaseBuilder(context, AppDatabase::class.java, name = "app-database")
          .fallbackToDestructiveMigration()
          .allowMainThreadQueries()
          .build()
      }
      return instance!!
    }
  }
}
