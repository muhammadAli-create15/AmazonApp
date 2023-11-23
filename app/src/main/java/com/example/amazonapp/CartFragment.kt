package com.example.amazonapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class CartFragment : Fragment() {
   private lateinit var itemArrayList:ArrayList<Items>

    private lateinit var name:Array<String>
    private lateinit var image:Array<Int>
    private lateinit var price:Array<Int>
    private lateinit var description:Array<String>
    private lateinit var cardView: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView=inflater.inflate(R.layout.fragment_cart, container, false)

/*        cardView=rootView.findViewById(R.id.edtCard)

        cardView.setOnClickListener {
            val intent= Intent(activity, ProductDetailsActivity::class.java)
            startActivity(intent)
        }*/

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_menu)


        name= arrayOf(
            "Laptops",
            "Female dresses",
            "Glasses",
            "Hats",
            "Headphones",
            "TShirts",
            "Watches",
            "Mobile Phones",
            "Shoes",
            "Bags/Purses",
            "Books",
            "Perfumes",
            "Suits"

        )
      image= arrayOf(
          R.drawable.laptop,
          R.drawable.hijab,
          R.drawable.img,
          R.drawable.img_1,
          R.drawable.img_2,
          R.drawable.shirts,
          R.drawable.img_9,
          R.drawable.mobile,
          R.drawable.img_3,
          R.drawable.img_4,
          R.drawable.img_5,
          R.drawable.perfume,
          R.drawable.suit

      )
        price= arrayOf(
            700000,
            100000,
            70000,
            20000,
            10000,
            20000,
            50000,
            25000,
            600000,
            700000,
            60000,
            40000,
            25000,
            150000
        )
        description= arrayOf(
            "Second hand dell laptop at an affordable price",
            "Female dresses at an affordable price",
            "Glasses at an affordable price",
            "Hats p at an affordable price",
            "Headphones at an affordable price",
            "TShirts at an affordable price",
            "Sports attires at an affordable price",
            "Watches at an affordable price",
            "Mobiles  at an affordable price",
            "Shoes at an affordable price",
            "Bags/Purses laptop at an affordable price",
            "Books at an affordable price",
            "Perfumes at an affordable price",
            "Suits at an affordable price"
        )

        recyclerView.layoutManager= LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        itemArrayList= arrayListOf()


        getData()

        val productAdapter=ProductAdapter(itemArrayList,this)
        recyclerView.adapter=productAdapter

     return rootView
    }

    private fun getData() {
   for(i in name.indices){
       val item=Items(name[i], image[i], price[i],description[i])
       itemArrayList.add(item)
      }
    }
}