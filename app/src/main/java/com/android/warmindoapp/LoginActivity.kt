package com.android.warmindoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.warmindoapp.data.AppDatabase
import com.android.warmindoapp.data.entity.Pengguna
import com.android.warmindoapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val username = binding.inputUsername.text.toString().trim()
        val password = binding.inputPassword.text.toString().trim()

        if (ValidationUtils.isTextNotEmpty(username) && ValidationUtils.isTextNotEmpty(password)) {
            if (ValidationUtils.isTextNotEmpty(username)) {
                val pengguna: Pengguna? = db.penggunaDao().getByUsername(username)
                if (pengguna != null && pengguna.password == password) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    // Redirect to DashboardActivity after successful login
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please input all field", Toast.LENGTH_SHORT).show()
        }
    }
}
