package com.example.grocery

class GroceryRepository(private val db: GroceryDatabase) {
    suspend fun insert(items: GroceryItems) = db.GroceryDatabase().insert(items)
    suspend fun delete(items: GroceryItems) = db.GroceryDatabase().delete(items)

    fun getAllGroceryItems() = db.GroceryDatabase().getAllGroceryItems()

}