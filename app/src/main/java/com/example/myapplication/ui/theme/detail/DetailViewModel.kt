package com.example.myapplication.ui.theme.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Product
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.database.CartItemEntity
import com.example.myapplication.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
class DetailViewModel : ViewModel() {
    var product = MutableStateFlow<Product?>(null)
    var isLoading = MutableStateFlow(false)
    var quantity = MutableStateFlow(1)
    fun loadProduct(id: Int) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                product.value = RetrofitClient.instance.getProduct(id)
            } catch (e: Exception) {
            } finally {
                isLoading.value = false
            }
        }
    }

    fun incrementQty() {
        quantity.value++
    }

    fun decrementQty() {
        if (quantity.value > 1) quantity.value--
    }

    fun addToCart(context: android.content.Context, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val currentProduct = product.value ?: return@launch
            val existing = db.cartDao().getById(currentProduct.id)
            if (existing != null) {
                val updated = existing.copy(quantity = existing.quantity + quantity.value)
                db.cartDao().insert(updated)
            } else {
                val item = CartItemEntity(
                    productId = currentProduct.id,
                    title = currentProduct.title,
                    price = currentProduct.price,
                    image = currentProduct.image,
                    quantity = quantity.value
                )
                db.cartDao().insert(item)
            }
            onSuccess()
        }
    }
}
