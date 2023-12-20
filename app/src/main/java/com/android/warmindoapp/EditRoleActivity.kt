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
  private lateinit var etRole : EditText
  private lateinit var etStatus : EditText
  private lateinit var btnSave : Button
  private lateinit var database : AppDatabase

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_edit_role)
    etRole = findViewById<EditText>(R.id.et_role)
    etStatus = findViewById<EditText>(R.id.et_status)
    btnSave = findViewById<Button>(R.id.btn_save)
    database =  AppDatabase.getInstance(applicationContext)

    val intent = intent.extras
    val id = intent?.getInt("id", 0) ?: 0
    if(intent != null){
      val role = database.roleDao().get(id)

      etRole.setText(role.role)
      etStatus.setText(role.status)
    }

    btnSave.setOnClickListener{
      if(etRole.text.isNotEmpty() && etStatus.text.isNotEmpty()){
        if (intent != null){
          // coding edit data
          database.roleDao().update(
            Role(
              intent.getInt("id"),
              etRole.text.toString(),
              etStatus.text.toString(),
            )
          )
        } else{
          // coding insert data
          database.roleDao().insertAll(
            Role(
              null,
              etRole.text.toString(),
              etStatus.text.toString(),
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