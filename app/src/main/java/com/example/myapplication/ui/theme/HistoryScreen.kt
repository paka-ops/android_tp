package com.example.myapplication.ui.theme


import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.database.OrderEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HistoryViewModel : ViewModel() {
    var orders = MutableStateFlow<List<OrderEntity>>(emptyList())

    fun loadOrders(context: Context) {
        viewModelScope.launch {
            AppDatabase.getDatabase(context).orderDao().getAll()
                .collect { orders.value = it }
        }
    }
}

@Composable
fun HistoryScreen(vm: HistoryViewModel = viewModel()) {
    val context = LocalContext.current
    val orders by vm.orders.collectAsState()

    LaunchedEffect(Unit) {
        vm.loadOrders(context)
    }

    if (orders.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Aucune commande pour le moment")
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(orders) { order ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Commande #${order.id}", style = MaterialTheme.typography.titleMedium)
                            Text(
                                "${String.format("%.2f", order.total)} €",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Text("Date: ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(order.date))}")
                        Text("${order.itemsCount} articles")
                        Text("Client: ${order.customerName}")
                    }
                }
            }
        }
    }
}
