package com.example.amazonapp

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import com.example.amazonapp.Model.Users
import com.example.amazonapp.Prevalent.Prevalent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var login: Button
    private lateinit var phoneNumber: EditText
    private lateinit var passwordInput: EditText
    private lateinit var admin: TextView
    private lateinit var noAdmin: TextView
    private lateinit var checkBox: CheckBox
    private lateinit var forgotPassword:TextView
    private var parentDbName = "Users"
    private var loadingBar: ProgressDialog? = null

    private val prefFileName = "LoginPrefs"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

       admin=findViewById(R.id.admin_panel_link)
        noAdmin=findViewById(R.id.not_admin_panel_link)
        phoneNumber=findViewById(R.id.login_phone_number_input)
        passwordInput=findViewById(R.id.login_password_input)
        login=findViewById(R.id.login_btn)
        checkBox=findViewById(R.id.remember_me_checkbox)
        forgotPassword=findViewById(R.id.forgot_password_link)
        loadingBar = ProgressDialog(this)

        login.setOnClickListener {
            loginUser()
        }


        admin.setOnClickListener {
            // Replace string literals with the resource ID
            login.text = getString(R.string.login_admin_text)
            admin.visibility = View.INVISIBLE
            noAdmin.visibility = View.VISIBLE
            parentDbName = "Admins"
            
        }
        noAdmin.setOnClickListener {
            // Replace string literals with the resource ID
            login.text = getString(R.string.login_text)
            admin.visibility = View.VISIBLE
            noAdmin.visibility = View.INVISIBLE
            parentDbName = "Users"
        }

        // Add an OnCheckedChangeListener to respond to checkbox changes
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (checkBox.isChecked) {
                val sharedPreferences: SharedPreferences = getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
                val rememberMeState = sharedPreferences.getBoolean("rememberMe", false)
                checkBox.isChecked = rememberMeState
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putBoolean("rememberMe", checkBox.isChecked)
                editor.apply()

                Toast.makeText(this, "Remember me checked", Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(this, "Remember me unchecked", Toast.LENGTH_SHORT).show()
            }
        }
        forgotPassword.setOnClickListener {
             val builder:AlertDialog.Builder=AlertDialog.Builder(this)
             builder.setTitle("Forgot password")
            val view=layoutInflater.inflate(R.layout.dialog_forgot_password,null)
            val userEmail=view.findViewById<EditText>(R.id.tvUserEmail)

            builder.setView(view)
            builder.setPositiveButton("Reset",DialogInterface.OnClickListener { _, i ->
                forgottPassword(userEmail)
            })
            builder.setNegativeButton("Close",DialogInterface.OnClickListener { _, i ->  })
            builder.show()

        }
    }

    private fun forgottPassword(userEmail: EditText?) {
        if(userEmail?.text.toString().isEmpty()){
            return

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail?.text.toString()).matches()){
            return
        }
        auth.sendPasswordResetEmail(userEmail?.text.toString()).addOnCompleteListener {
            task ->
            if(task.isSuccessful){
                Toast.makeText(this,"Email has been sent",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun loginUser() {
        val phone = phoneNumber.text.toString()
        val password = passwordInput.text.toString()

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show()
        } else {

            loadingBar?.setTitle("Login Account");
            loadingBar?.setMessage("Please wait, while we are checking the credentials.");
            loadingBar?.setCanceledOnTouchOutside(false);
            loadingBar?.show()

            allowAccessToAccount(phone, password)
        }
    }
    private fun allowAccessToAccount(phone: String, password: String) {

        val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
        rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists()) {
                    val usersData: Users? = dataSnapshot.child(parentDbName).child(phone).getValue(Users::class.java)
                    if (usersData?.phone == phone) {
                        if (usersData.password == password) {
                            if (parentDbName == "Admins") {
                                Toast.makeText(this@LoginActivity, "Welcome Admin, you are logged in Successfully...", Toast.LENGTH_SHORT).show()
                                loadingBar?.dismiss()
                                val intent = Intent(this@LoginActivity, AdminCategoryActivity::class.java)
                                startActivity(intent)
                            } else if (parentDbName == "Users") {
                                Toast.makeText(this@LoginActivity, "logged in Successfully...", Toast.LENGTH_SHORT).show()
                                loadingBar?.dismiss()
                                val intent = Intent(this@LoginActivity, ProductDisplay::class.java)
                                Prevalent.currentOnlineUser = usersData
                                startActivity(intent)
                            }
                        } else {
                            loadingBar?.dismiss()
                            Toast.makeText(this@LoginActivity, "Password is incorrect.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Account with this $phone number do not exist.", Toast.LENGTH_SHORT).show()
                    loadingBar?.dismiss()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}