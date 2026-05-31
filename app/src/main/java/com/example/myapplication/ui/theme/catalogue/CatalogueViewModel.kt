package com.example.myapplication.ui.theme.catalogue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Product
import com.example.myapplication.data.network.RetrofitClient

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CatalogViewModel : ViewModel() {
    var products = MutableStateFlow<List<Product>>(emptyList())
        private set
    var isLoading = MutableStateFlow(false)
    var errorMessage = MutableStateFlow<String?>(null)
    var categories = MutableStateFlow<List<String>>(emptyList())
    var selectedCategory = MutableStateFlow<String?>(null)
    init {
        loadProducts()
    }
    fun loadProducts() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                errorMessage.value = null
                val result = RetrofitClient.instance.getProducts()
                products.value = result
                val cats = mutableListOf<String>()
                result.forEach { p ->
                    if (!cats.contains(p.category)) {
                        cats.add(p.category)
                    }
                }
                categories.value = cats
            } catch (e: Exception) {
                errorMessage.value = "Erreur: ${e.message}"
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }
    fun filterByCategory(category: String) {
        selectedCategory.value = category
        viewModelScope.launch {
            isLoading.value = true
            try {
                products.value = if (category == "Tous") {
                    RetrofitClient.instance.getProducts()
                } else {
                    RetrofitClient.instance.getProductsByCategory(category)
                }
            } catch (e: Exception) {
                errorMessage.value = "Erreur filtre"
            } finally {
                isLoading.value = false
            }
        }
    }
}
