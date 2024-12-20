package com.example.jetpackpracticeapplicatiion.ui.theme

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://fakestoreapi.com/") // Replace with the actual base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Correctly create the ApiInterfaces service
    val apiService: ApiInterfaces = retrofit.create(ApiInterfaces::class.java)
}


