package com.example.myapplication.ui.theme.cart

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.data.database.CartItemEntity

@Composable
fun CartScreen(navController: NavController, vm: CartViewModel = viewModel()) {
    val context = LocalContext.current
    val items by vm.items.collectAsState()
    val total by vm.totalPrice.collectAsState()

    LaunchedEffect(Unit) {
        vm.loadCart(context)
    }

    if (items.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.outline
                )
                Text("Votre panier est vide")
            }
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(items, key = { it.productId }) { item ->
                    CartItemRow(
                        item = item,
                        onQtyChange = { vm.updateQuantity(context, item, it) },
                        onDelete = { vm.deleteItem(context, item) }
                    )
                    Divider()
                }
            }

            Surface(
                shadowElevation = 8.dp,
                tonalElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total:", style = MaterialTheme.typography.titleLarge)
                        Text(
                            "${String.format("%.2f", total)} €",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Button(
                        onClick = { navController.navigate("checkout") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Passer commande")
                    }
                }
            }
        }
    }
}

@Composable
private fun CartItemRow(
    item: CartItemEntity,
    onQtyChange: (Int) -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.image,
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text(item.title, maxLines = 2)
            Text("${item.price} €")
            Text(
                "Sous-total: ${String.format("%.2f", item.price * item.quantity)} €",
                style = MaterialTheme.typography.labelSmall
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onQtyChange(item.quantity - 1) }) {
                Icon(Icons.Default.Remove, null)
            }
            Text("${item.quantity}")
            IconButton(onClick = { onQtyChange(item.quantity + 1) }) {
                Icon(Icons.Default.Add, null)
            }
        }

        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, null, tint = MaterialTheme.colorScheme.error)
        }
    }
}
