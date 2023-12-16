package com.android.warmindoapp.data.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.android.warmindoapp.data.entity.Role

@Dao
interface RoleDao {
  @Query("SELECT * FROM role")
  fun getAll(): List<Role>

  @Insert
  fun insertAll(vararg roles: Role)

  @Delete
  fun delete(role: Role)

  @Query("SELECT * FROM role WHERE idrole = :id")
  fun get(id: Int): Role

  @Update
  fun update(role: Role)
}