package com.example.amazonapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProductDetailsActivity : AppCompatActivity() {
    private var currentNumber = 0
    private lateinit var addCart:FloatingActionButton
    private lateinit var decrementButton:Button
    private lateinit var incrementButton:Button

    private lateinit var pdtImage:ImageView
    private lateinit var pdtName:TextView
    private lateinit var pdtDescription:TextView
    private lateinit var pdtPrice:TextView

    private lateinit var displayNumber: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        pdtImage=findViewById(R.id.product_image_details)
        pdtName=findViewById(R.id.product_detail_name)
        pdtDescription=findViewById(R.id.product_detail_description)
        pdtPrice=findViewById(R.id.product_detail_price)

        displayNumber = findViewById(R.id.displayNumber)
        decrementButton = findViewById(R.id.decrementButton)
        incrementButton = findViewById(R.id.incrementButton)

        displayNumber.text = currentNumber.toString()

        decrementButton.setOnClickListener {
            if (currentNumber > 0) {
                currentNumber--
                displayNumber.text = currentNumber.toString()
                // Update your business logic with the decremented value
            }
        }

        incrementButton.setOnClickListener {
            currentNumber++
            displayNumber.text = currentNumber.toString()
            // Update your business logic with the incremented value
        }
    }
}