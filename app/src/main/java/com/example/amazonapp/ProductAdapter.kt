package com.example.amazonapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ProductAdapter(val arrayList: ArrayList<Items>, val context: CartFragment):
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
   inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

       val  productName:TextView=itemView.findViewById(R.id.product_name)
       val  productImage:ImageView=itemView.findViewById(R.id.product_image)
       val  productPrice:TextView=itemView.findViewById(R.id.product_price)
       val  productDescription:TextView=itemView.findViewById(R.id.product_description)
       val cardView:CardView=itemView.findViewById(R.id.edtCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.product_items_layout,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem=arrayList[position]


        holder.productName.text=currentItem.name
        holder.productImage.setImageResource(currentItem.image)
        holder.productPrice.text= currentItem.price.toString()
        holder.productDescription.text=currentItem.description


        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
       return arrayList.size
    }

}