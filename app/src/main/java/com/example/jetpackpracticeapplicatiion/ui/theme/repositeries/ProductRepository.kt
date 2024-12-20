package com.example.jetpackpracticeapplicatiion.ui.theme.repositeries

import com.example.jetpackpracticeapplicatiion.ui.theme.ApiInterfaces
import com.example.jetpackpracticeapplicatiion.ui.theme.RetrofitInstance
import com.example.jetpackpracticeapplicatiion.ui.theme.models.Product


class ProductRepository(private val api: ApiInterfaces) {

    suspend fun fetchProducts(limit: Int): List<Product> = api.getProducts(limit)

    suspend fun fetchProductDetail(id: Int): Product = api.getProductDetail(id)
}




