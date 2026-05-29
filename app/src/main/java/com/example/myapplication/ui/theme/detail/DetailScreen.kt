package com.example.myapplication.ui.theme.detail

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    productId: Int,
    navController: NavController,
    vm: DetailViewModel = viewModel()
) {
    val context = LocalContext.current
    val product by vm.product.collectAsState()
    val isLoading by vm.isLoading.collectAsState()
    val qty by vm.quantity.collectAsState()

    // Débutant : charge dans un LaunchedEffect sans clé parfois
    LaunchedEffect(productId) {
        vm.loadProduct(productId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détail") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->
        if (isLoading || product == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            val p = product!!
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = p.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    p.title,
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    "${p.price} €",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    "Catégorie: ${p.category}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(8.dp))

                Text(p.description)

                Spacer(Modifier.height(24.dp))

                // Sélecteur quantité
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Quantité:", modifier = Modifier.weight(1f))
                    IconButton(onClick = { vm.decrementQty() }) {
                        Icon(Icons.Default.Remove, null)
                    }
                    Text("$qty", style = MaterialTheme.typography.titleLarge)
                    IconButton(onClick = { vm.incrementQty() }) {
                        Icon(Icons.Default.Add, null)
                    }
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        vm.addToCart(context) {
                            Toast.makeText(context, "Ajouté au panier!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.ShoppingCart, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Ajouter au panier")
                }
            }
        }
    }
}