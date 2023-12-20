package com.android.warmindoapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.warmindoapp.data.AppDatabase
import com.android.warmindoapp.data.entity.Role
class EditRoleActivity :AppCompatActivity(){
  private lateinit var role : EditText
  private lateinit var status : EditText
  private lateinit var btnSave : Button
  private lateinit var database : AppDatabase

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_edit_role)
    role = findViewById(R.id.role)
    status = findViewById(R.id.status)
    btnSave = findViewById(R.id.btn_save)
    database =  AppDatabase.getInstance(applicationContext)

    val intent = intent.extras
    val id = intent?.getInt("id", 0) ?: 0
    if(intent != null){
      val user = database.roleDao().get(id)

      role.setText(user.role)
      status.setText(user.status)
    }

    btnSave.setOnClickListener{
      if(role.text.isNotEmpty() && status.text.isNotEmpty()){
        if (intent != null){
          // coding edit data
          database.roleDao().update(
            Role(
              intent.getInt("id"),
              role.text.toString(),
              status.text.toString(),
            )
          )
        } else{
          // coding insert data
          database.roleDao().insertAll(
            Role(
              null,
              role.text.toString(),
              status.text.toString(),
            )
          )
        }
        finish()
      }else{
        Toast.makeText(applicationContext, "Silahkan isi smua data dengan valid", Toast.LENGTH_SHORT).show()
      }
    }
  }
}