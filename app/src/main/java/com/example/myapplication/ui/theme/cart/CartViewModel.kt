package com.example.myapplication.ui.theme.cart


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.database.CartItemEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    // Débutant : expose le Flow direct sans transformation
    private var _items = MutableStateFlow<List<CartItemEntity>>(emptyList())
    val items = _items.asStateFlow()

    var totalPrice = MutableStateFlow(0.0)

    fun loadCart(context: Context) {
        viewModelScope.launch {
            AppDatabase.getDatabase(context).cartDao().getAll()
                .collect { list ->
                    _items.value = list
                    // Calcul du total (débutant : fait à la main)
                    var total = 0.0
                    list.forEach { item ->
                        total += item.price * item.quantity
                    }
                    totalPrice.value = total
                }
        }
    }

    fun updateQuantity(context: Context, item: CartItemEntity, newQty: Int) {
        viewModelScope.launch {
            if (newQty <= 0) {
                deleteItem(context, item)
            } else {
                val updated = item.copy(quantity = newQty)
                AppDatabase.getDatabase(context).cartDao().insert(updated)
            }
        }
    }

    fun deleteItem(context: Context, item: CartItemEntity) {
        viewModelScope.launch {
            AppDatabase.getDatabase(context).cartDao().delete(item)
        }
    }

    fun clearCart(context: Context) {
        viewModelScope.launch {
            AppDatabase.getDatabase(context).cartDao().clearAll()
        }
    }
}