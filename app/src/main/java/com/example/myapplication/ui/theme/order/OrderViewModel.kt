package com.example.myapplication.ui.theme.order



import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.database.OrderEntity
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {
    fun placeOrder(
        context: Context,
        name: String,
        phone: String,
        address: String,
        city: String,
        total: Double,
        itemsCount: Int,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)

            // Sauvegarde commande
            val order = OrderEntity(
                total = total,
                customerName = name,
                itemsCount = itemsCount,
                date = System.currentTimeMillis()
            )
            db.orderDao().insert(order)

            // Vide le panier
            db.cartDao().clearAll()

            onSuccess()
        }
    }
}