package com.example.myapplication.ui.theme.catalogue

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.component.ProductCard

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(navController: NavController, vm: CatalogViewModel = viewModel()) {
    val products by vm.products.collectAsState()
    val isLoading by vm.isLoading.collectAsState()
    val error by vm.errorMessage.collectAsState()
    val categories by vm.categories.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        ScrollableTabRow(
            selectedTabIndex = 0,
            edgePadding = 0.dp
        ) {
            Tab(
                selected = vm.selectedCategory.value == null,
                onClick = { vm.filterByCategory("Tous") },
                text = { Text("Tous") }
            )
            categories.forEach { cat ->
                Tab(
                    selected = vm.selectedCategory.value == cat,
                    onClick = { vm.filterByCategory(cat) },
                    text = { Text(cat) }
                )
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                isLoading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                error != null -> Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(error!!, color = MaterialTheme.colorScheme.error)
                    Button(onClick = { vm.loadProducts() }) {
                        Text("Réessayer")
                    }
                }
                products.isEmpty() -> Text(
                    "Aucun produit",
                    modifier = Modifier.align(Alignment.Center)
                )
                else -> LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(products) { product ->
                        ProductCard(product = product) {
                            navController.navigate("detail/${product.id}")
                        }
                    }
                }
            }
        }
    }
}
