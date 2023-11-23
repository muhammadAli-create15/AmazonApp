package com.example.amazonapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {
    private lateinit var joinNowButton: Button
    private lateinit var loginButton:Button
    private var loadingBar: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        joinNowButton=findViewById(R.id.main_join_now_btn)
        loginButton=findViewById(R.id.main_login_btn)
        loadingBar=ProgressDialog(this)


        joinNowButton.setOnClickListener {
            val intent=Intent(this,Registering::class.java)
            startActivity(intent)
        }
        loginButton.setOnClickListener {
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}