package com.example.amazonapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.*

class AdminAddNewProductActivity : AppCompatActivity() {
    private var categoryName: String = ""
    private var description: String = ""
    private var price: String = ""
    private var pname: String = ""
    private var saveCurrentDate: String = ""
    private var saveCurrentTime: String = ""
    private lateinit var addNewProductButton: Button
    private lateinit var inputProductImage: ImageView
    private lateinit var inputProductName: EditText
    private lateinit var inputProductDescription: EditText
    private lateinit var inputProductPrice: EditText
    private val galleryPick = 1
    private var imageUri: Uri? = null
    private var productRandomKey: String = ""
    private var downloadImageUrl: String = ""
    private lateinit var productImagesRef: StorageReference
    private lateinit var productsRef: DatabaseReference
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_new_product)

        categoryName = intent.extras?.getString("category") ?: ""
        productImagesRef = FirebaseStorage.getInstance().reference.child("Product Images")
        productsRef = FirebaseDatabase.getInstance().reference.child("Products")

        addNewProductButton = findViewById(R.id.add_new_product)
        inputProductImage = findViewById(R.id.select_product_image)
        inputProductName = findViewById(R.id.product_name)
        inputProductDescription = findViewById(R.id.product_description)
        inputProductPrice = findViewById(R.id.product_price)
        progressBar = findViewById(R.id.progress_bar)

        progressBar.visibility = View.INVISIBLE

        inputProductImage.setOnClickListener {
            openGallery()
        }

        addNewProductButton.setOnClickListener {
            validateProductData()
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
            inputProductImage.setImageURI(imageUri)
        }
    }

    private fun validateProductData() {
        description = inputProductDescription.text.toString()
        price = inputProductPrice.text.toString()
        pname = inputProductName.text.toString()

        if (imageUri == null) {
            Toast.makeText(this, "Product image is mandatory...", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Please write product description...", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(price)) {
            Toast.makeText(this, "Please write product Price...", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(pname)) {
            Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show()
        } else {
            storeProductInformation()
        }
    }

    private fun storeProductInformation() {
        progressBar.visibility = View.VISIBLE

        val calendar = Calendar.getInstance()

        val currentDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        saveCurrentDate = currentDate.format(calendar.time)

        val currentTime = SimpleDateFormat("HH:mm:ss a", Locale.getDefault())
        saveCurrentTime = currentTime.format(calendar.time)

        productRandomKey = saveCurrentDate + saveCurrentTime

        val filePath = productImagesRef.child(imageUri?.lastPathSegment + productRandomKey + ".jpg")

        val uploadTask = filePath.putFile(imageUri!!)

        uploadTask.addOnFailureListener { e ->
            progressBar.visibility = View.INVISIBLE
            val message = e.toString()
            Toast.makeText(this@AdminAddNewProductActivity, "Error: $message", Toast.LENGTH_SHORT).show()

        }.addOnSuccessListener {
            Toast.makeText(this@AdminAddNewProductActivity, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show()

            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    throw task.exception!!
                }
                downloadImageUrl = filePath.downloadUrl.toString()
                filePath.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    downloadImageUrl = task.result.toString()
                    Toast.makeText(this@AdminAddNewProductActivity, "Got the Product image URL Successfully...", Toast.LENGTH_SHORT).show()
                    saveProductInfoToDatabase()
                }
            }
        }
    }

    private fun saveProductInfoToDatabase() {
        val productMap = HashMap<String, Any>()
        productMap["pid"] = productRandomKey
        productMap["date"] = saveCurrentDate
        productMap["time"] = saveCurrentTime
        productMap["description"] = description
        productMap["image"] = downloadImageUrl
        productMap["category"] = categoryName
        productMap["price"] = price
        productMap["pname"] = pname

        productsRef.child(productRandomKey).updateChildren(productMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@AdminAddNewProductActivity, AdminCategoryActivity::class.java)
                    startActivity(intent)

                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this@AdminAddNewProductActivity, "Product is added successfully..", Toast.LENGTH_SHORT).show()
                } else {
                    progressBar.visibility = View.INVISIBLE
                    val message = task.exception.toString()
                    Toast.makeText(this@AdminAddNewProductActivity, "Error: $message", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
