package com.example.jetpackpracticeapplicatiion.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jetpackpracticeapplicatiion.ui.theme.ApiInterfaces
import com.example.jetpackpracticeapplicatiion.ui.theme.RetrofitInstance
import com.example.jetpackpracticeapplicatiion.ui.theme.models.Product
import com.example.jetpackpracticeapplicatiion.ui.theme.repositeries.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductViewModel : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val repository = ProductRepository(RetrofitInstance.apiService)
    private var currentPage = 0
    private var isFetching = false

    fun fetchNextPage(limit: Int = 5) {
        if (isFetching) return // Prevent multiple simultaneous requests
        isFetching = true
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                currentPage++
                val newProducts = repository.fetchProducts(limit = currentPage * limit)
                _products.postValue((_products.value ?: emptyList()) + newProducts)
            } catch (e: Exception) {
                e.printStackTrace() // Handle error
            } finally {
                isFetching = false
                _isLoading.postValue(false) // Turn off loading when the data is fetched
            }
        }
    }
}

