package com.example.jetpackpracticeapplicatiion.ui.theme.models

// Product data class
data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val image: String,
    val price: Double,
    val rating: Rating // Change `Double` to `Rating`
)

data class Rating(
    val rate: Double,
    val count: Int
)
