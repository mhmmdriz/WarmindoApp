// Membuat database
package com.android.warmindoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.warmindoapp.data.dao.PenggunaDao
import com.android.warmindoapp.data.entity.Pengguna
import com.android.warmindoapp.data.dao.RoleDao
import com.android.warmindoapp.data.dao.PenggunaroleDao
import com.android.warmindoapp.data.entity.Role

@Database(entities = [Pengguna::class, Role::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
  abstract fun penggunaDao(): PenggunaDao
  abstract fun roleDao(): RoleDao // Add this line for RoleDao
  abstract fun penggunaRoleDao(): PenggunaroleDao // Add this line for RoleDao

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
