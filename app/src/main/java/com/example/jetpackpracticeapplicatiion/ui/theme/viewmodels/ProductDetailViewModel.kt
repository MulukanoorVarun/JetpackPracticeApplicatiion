package com.example.jetpackpracticeapplicatiion.ui.theme.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackpracticeapplicatiion.ui.theme.RetrofitInstance
import com.example.jetpackpracticeapplicatiion.ui.theme.models.Product
import com.example.jetpackpracticeapplicatiion.ui.theme.repositeries.ProductRepository
import kotlinx.coroutines.launch

class ProductDetailViewModel : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    private val repository = ProductRepository(RetrofitInstance.apiService)

    // Example function to fetch product by ID
    fun fetchProductDetail(id: Int) {
        viewModelScope.launch {
            try {
                val productDetail = repository.fetchProductDetail(id)
                _product.value = productDetail
            } catch (e: Exception) {
                // Handle errors appropriately
                // You could also post an error state if you prefer
            }
        }
    }
}

