package com.example.myapplication.data
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating? = null
)
data class Rating(
    val rate: Double,
    val count: Int
)

data class CartItem(
    val productId: Int,
    val title: String,
    val price: Double,
    val image: String,
    var quantity: Int
)

data class Order(
    val id: Int = 0,
    val date: Long = System.currentTimeMillis(),
    val total: Double,
    val customerName: String,
    val itemsCount: Int
)
