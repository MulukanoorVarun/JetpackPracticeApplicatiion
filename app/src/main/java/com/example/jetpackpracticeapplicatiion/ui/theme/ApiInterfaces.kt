package com.example.jetpackpracticeapplicatiion.ui.theme

import com.example.jetpackpracticeapplicatiion.ui.theme.models.Product
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterfaces {
    // GET request for product detail
    @GET("products/{id}")
    suspend fun getProductDetail(@Path("id") id: Int): Product

    // GET request for a list of products
    @GET("products")
    suspend fun getProducts(@Query("limit") limit: Int): List<Product>
}

