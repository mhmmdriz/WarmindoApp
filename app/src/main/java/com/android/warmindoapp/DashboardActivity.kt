package com.android.warmindoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
    }

    fun onManageRoleClick(view: View) {
        val intent = Intent(this, ManageRoleActivity::class.java)
        startActivity(intent)
    }

    fun onDataWarungClick(view: View) {
        val intent = Intent(this, ManageWarungActivity::class.java)
        startActivity(intent)
    }
}