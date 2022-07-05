package com.example.grocery

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grocery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), GroceryItemsAdapter.GroceryItemClickInterface {

    private lateinit var binding: ActivityMainBinding
    private lateinit var list: List<GroceryItems>
    private lateinit var groceryItemsAdapter: GroceryItemsAdapter
    private lateinit var groceryViewModal: GroceryViewModal


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = ArrayList()
        groceryItemsAdapter = GroceryItemsAdapter(list, this )

        binding.listItems.layoutManager = LinearLayoutManager(this)
        binding.listItems.adapter = groceryItemsAdapter
        val groceryRepository = GroceryRepository(GroceryDatabase(this))
        val factory = GroceryViewModalFactory(groceryRepository)
        groceryViewModal = ViewModelProvider(this, factory)[GroceryViewModal::class.java]
        groceryViewModal.getAllGroceryItems().observe(this, Observer {
            groceryItemsAdapter.list = it
            groceryItemsAdapter.notifyDataSetChanged()
        })


        binding.floatingActionButton.setOnClickListener {
            openDialog()
        }
    }


    private fun openDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.add_dialog)
        val cancelBtn = dialog.findViewById<Button>(R.id.cancelButton)
        val addBtn = dialog.findViewById<Button>(R.id.addButton)
        val setItemName = dialog.findViewById<EditText>(R.id.setItemName)
        val setQuantity = dialog.findViewById<EditText>(R.id.setQuantity)
        val setRate = dialog.findViewById<EditText>(R.id.setRate)
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        addBtn.setOnClickListener {
            val itemName: String = setItemName.text.toString()
            val quantity: String = setQuantity.text.toString()
            val rate: String = setRate.text.toString()

            if (itemName.isNotEmpty() && quantity.isNotEmpty() && rate.isNotEmpty()) {
                val item = GroceryItems(itemName , quantity.toInt(), rate.toInt())
                groceryViewModal.insert(item)
                groceryItemsAdapter.notifyDataSetChanged()
                dialog.dismiss()
            } else{
                Toast.makeText(this , "full fill the details" , Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }

    override fun onItemClick(groceryItems: GroceryItems) {
        groceryViewModal.delete(groceryItems)
        groceryItemsAdapter.notifyDataSetChanged()
    }
}