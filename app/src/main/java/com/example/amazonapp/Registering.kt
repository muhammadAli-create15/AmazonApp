package com.example.amazonapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Registering : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var createAccount: Button
    private lateinit var inputName: EditText
    private lateinit var inputEmail:EditText
    private lateinit var inputPhone:EditText
    private lateinit var inputPassword:EditText
    private var loadingBar: ProgressDialog? = null

    //  private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registering)

        auth= FirebaseAuth.getInstance()

        createAccount=findViewById(R.id.register_btn)
        inputName=findViewById(R.id.register_username_input)
        inputEmail=findViewById(R.id.register_email_input)
        inputPhone=findViewById(R.id.register_phone_number_input)
        inputPassword=findViewById(R.id.register_password_input)
        loadingBar = ProgressDialog(this)

        createAccount.setOnClickListener {
            registerAccount()
        }



    }

    private fun registerAccount() {
        val name=inputName?.text.toString()
        val email=inputEmail?.text.toString()
        val phone=inputPhone?.text.toString()
        val password=inputPassword?.text.toString()

        if(name.isEmpty()||email.isEmpty()||phone.isEmpty()||password.isEmpty()){
            Toast.makeText(this,"Please fill in the missing fields",Toast.LENGTH_SHORT).show()
            return
        }
        if (isEmailValid(email)) {
            // Email is valid
        } else {
            // Email is not valid
            inputEmail.error = "Invalid email format"
        }
        loadingBar?.setTitle("Create Account");
        loadingBar?.setMessage("Please wait, while we are checking the credentials.");
        loadingBar?.setCanceledOnTouchOutside(false);
        loadingBar?.show();


        validatePhoneNumber(name,email,phone,password)

    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validatePhoneNumber(name: String, email: String, phone: String, password: String) {
        val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
        rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.child("Users").child(phone).exists()) {
                    val userdataMap = HashMap<String, Any>()
                    userdataMap["phone"] = phone
                    userdataMap["password"] = password
                    userdataMap["name"] = name
                    userdataMap["email"] = email // Add email to the data

                    rootRef.child("Users").child(phone).updateChildren(userdataMap)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this@Registering, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show()
                                //progressBar.visibility = View.INVISIBLE

                                val intent = Intent(this@Registering, ProductDisplay::class.java)
                                intent.putExtra("EXTRA_NAME",name)
                                startActivity(intent)
                            } else {
                                //progressBar.visibility = View.INVISIBLE
                                Toast.makeText(this@Registering, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this@Registering, "This $phone already exists.", Toast.LENGTH_SHORT).show()
                    // progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this@Registering, "Please try again using another phone number.", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@Registering, MainActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database errors if necessary
            }
        })
    }
}