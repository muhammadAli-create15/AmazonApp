package com.example.amazonapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.amazonapp.Prevalent.Prevalent
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import de.hdodenhof.circleimageview.CircleImageView

class Settings : AppCompatActivity() {
    private lateinit var profileImageView: CircleImageView
    private lateinit var phoneNo:EditText
    private lateinit var address:EditText
    private lateinit var fullName:EditText
    private lateinit var changeImage:TextView
    private lateinit var upDate:TextView
    private lateinit var close:TextView
    private lateinit var database:DatabaseReference

    private val galleryPick = 1
    private var downloadImageUrl: String = ""


    private var imageUri: Uri? = null
    private var myUrl = ""
    private var uploadTask: UploadTask? = null
    private lateinit var storageProfilePictureRef: StorageReference
    private var checker = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        storageProfilePictureRef = FirebaseStorage.getInstance().reference.child("Profile pictures")

        profileImageView = findViewById(R.id.settings_profile_image)
        upDate=findViewById(R.id.update_account_settings_btn)
        close=findViewById(R.id.close_settings_btn)

        phoneNo=findViewById(R.id.settings_phone_number)
        address=findViewById(R.id.settings_address)
        fullName=findViewById(R.id.settings_full_name)
        changeImage=findViewById(R.id.profile_image_change_btn)

        upDate.setOnClickListener {
            val phone=phoneNo.text.toString()
            val addressEmail=address.text.toString()
            val username=fullName.text.toString()

            updateData(phone,addressEmail,username)
        }


        close.setOnClickListener {
            finish()
        }
        changeImage.setOnClickListener {
            openGallery()
        }

    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, galleryPick)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == galleryPick && resultCode == RESULT_OK && data != null) {
            imageUri = data.data
            profileImageView.setImageURI(imageUri)
        }
    }

    private fun updateData(phone: String, addressEmail: String, username: String) {

        if (imageUri == null) {
            Toast.makeText(this, "Profile image is mandatory...", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please input phone number...", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(addressEmail)) {
            Toast.makeText(this, "Please input   the email address...", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please input your username...", Toast.LENGTH_SHORT).show()
        } else {
            val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
            val user = mapOf<String, String>(
                "name" to username,
                "email" to addressEmail,
                "phone" to phone
            )
            ref.child(Prevalent.currentOnlineUser?.getPhone().toString()).updateChildren(user as Map<String, Any>)

           // progressDialog.dismiss()

            startActivity(Intent(this,  ProductDisplay::class.java))
            Toast.makeText(this, "Profile Info updated successfully.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}