package com.example.grocery

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.example.grocery.databinding.GroceryAllItemBinding

class GroceryItemsAdapter(
    var list: List<GroceryItems>,
    private val groceryItemClickInterface: GroceryItemClickInterface
) : RecyclerView.Adapter<GroceryItemsAdapter.GroceryViewHolder>() {

    inner class GroceryViewHolder(itemView: GroceryAllItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val itemName = itemView.itemName
        val itemQuantity = itemView.itemQuantity
        val itemRate = itemView.itemRate
        val totalRate = itemView.totalRate
        val deleteItem = itemView.deleteItem
    }

    interface GroceryItemClickInterface {
        fun onItemClick(groceryItems: GroceryItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        return GroceryViewHolder(
            GroceryAllItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        holder.itemName.text = list[position].itemName
        holder.itemQuantity.text = list[position].itemQuantity.toString()
        holder.itemRate.text = list[position].itemPrice.toString()
        "Total amount: ${list[position].itemQuantity * list[position].itemPrice}".also {
            holder.totalRate.text = it
        }
        holder.deleteItem.setOnClickListener {
            groceryItemClickInterface.onItemClick(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}