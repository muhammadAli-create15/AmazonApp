package com.example.amazonapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class AdminCategoryActivity : AppCompatActivity() {
    private lateinit var shirts:ImageView
    private lateinit var sportShirts:ImageView
    private lateinit var femaleDresses:ImageView
    private lateinit var pursesBagsWallets:ImageView
    private lateinit var glasses:ImageView
    private lateinit var hatsCaps:ImageView
    private lateinit var shoes:ImageView
    private lateinit var headPhones:ImageView
    private lateinit var laptops:ImageView
    private lateinit var watches:ImageView
    private lateinit var mobilePhones:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_category)

        shirts=findViewById(R.id.t_shirts)
        sportShirts=findViewById(R.id.sports_t_shirts)
        femaleDresses=findViewById(R.id.female_dresses)
        pursesBagsWallets=findViewById(R.id.purses_bags_wallets)
        glasses=findViewById(R.id.glasses)
        hatsCaps=findViewById(R.id.hats_caps)
        headPhones=findViewById(R.id.headphones_handfree)
        shoes=findViewById(R.id.shoes)
        laptops=findViewById(R.id.laptop_pc)
        watches=findViewById(R.id.watches)
        mobilePhones=findViewById(R.id.mobilephones)

        shirts.setOnClickListener {
            val intent = Intent(this@AdminCategoryActivity, AdminAddNewProductActivity::class.java)
            intent.putExtra("category", "tShirts")
            startActivity(intent)
        }

        sportShirts.setOnClickListener {
            val intent = Intent(this@AdminCategoryActivity, AdminAddNewProductActivity::class.java)
            intent.putExtra("category", "Sports tShirts")
            startActivity(intent)
        }

        femaleDresses.setOnClickListener {
            val intent = Intent(this@AdminCategoryActivity, AdminAddNewProductActivity::class.java)
            intent.putExtra("category", "Female Dresses")
            startActivity(intent)
        }

        glasses.setOnClickListener {
            val intent = Intent(this@AdminCategoryActivity, AdminAddNewProductActivity::class.java)
            intent.putExtra("category", "Glasses")
            startActivity(intent)
        }

        hatsCaps.setOnClickListener {
            val intent = Intent(this@AdminCategoryActivity, AdminAddNewProductActivity::class.java)
            intent.putExtra("category", "Hats Caps")
            startActivity(intent)
        }

        pursesBagsWallets.setOnClickListener {
            val intent = Intent(this@AdminCategoryActivity, AdminAddNewProductActivity::class.java)
            intent.putExtra("category", "Wallets Bags Purses")
            startActivity(intent)
        }

        shoes.setOnClickListener {
            val intent = Intent(this@AdminCategoryActivity, AdminAddNewProductActivity::class.java)
            intent.putExtra("category", "Shoes")
            startActivity(intent)
        }

        headPhones.setOnClickListener {
            val intent = Intent(this@AdminCategoryActivity, AdminAddNewProductActivity::class.java)
            intent.putExtra("category", "HeadPhones HandFree")
            startActivity(intent)
        }

        laptops.setOnClickListener {
            val intent = Intent(this@AdminCategoryActivity, AdminAddNewProductActivity::class.java)
            intent.putExtra("category", "Laptops")
            startActivity(intent)
        }

        watches.setOnClickListener {
            val intent = Intent(this@AdminCategoryActivity, AdminAddNewProductActivity::class.java)
            intent.putExtra("category", "Watches")
            startActivity(intent)
        }

        mobilePhones.setOnClickListener {
            val intent = Intent(this@AdminCategoryActivity, AdminAddNewProductActivity::class.java)
            intent.putExtra("category", "Mobile Phones")
            startActivity(intent)
        }



    }
}