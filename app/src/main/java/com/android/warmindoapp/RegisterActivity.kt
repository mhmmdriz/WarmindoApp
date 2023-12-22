package com.android.warmindoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.warmindoapp.data.AppDatabase
import com.android.warmindoapp.data.entity.Pengguna
import com.android.warmindoapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)
        binding.btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val namapengguna = binding.inputNamaPengguna.text.toString()
        val username = binding.inputUsername.text.toString()
        val password = binding.inputPassword.text.toString()

        if (ValidationUtils.isTextNotEmpty(namapengguna) &&
            ValidationUtils.isTextNotEmpty(username) &&
            ValidationUtils.isTextNotEmpty(password)
        ) {
            if (ValidationUtils.isTextNotEmpty(username)) {
                val existingUser: Pengguna? = db.penggunaDao().getByUsername(username)
                if (existingUser == null) {
                    val pengguna = Pengguna(idpengguna = "", username = username, password = password, namapengguna = namapengguna, status = "aktif", idrole = 1, foto = "")
                    db.penggunaDao().insertAll(pengguna)
                    Toast.makeText(this, "User registered", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Email is already registered", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please input all field", Toast.LENGTH_SHORT).show()
        }
    }
}
