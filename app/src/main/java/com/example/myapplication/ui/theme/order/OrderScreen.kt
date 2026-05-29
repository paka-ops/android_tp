package com.example.myapplication.ui.theme.order



import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.cart.CartViewModel

@SuppressLint("RememberReturnType")
@Composable
fun OrderScreen(navController: NavController, vm: OrderViewModel = viewModel()) {
    val context = LocalContext.current
    val cartVm = remember { CartViewModel() }  // Débutant : crée une nouvelle instance !

    val cartItems by cartVm.items.collectAsState()
    val total by cartVm.totalPrice.collectAsState()

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    // Charge le panier
    LaunchedEffect(Unit) {
        cartVm.loadCart(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Finaliser la commande", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nom complet *") },
            modifier = Modifier.fillMaxWidth(),
            isError = showError && name.isBlank()
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Téléphone *") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = showError && (phone.isBlank() || !phone.all { it.isDigit() })
        )

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Adresse *") },
            modifier = Modifier.fillMaxWidth(),
            isError = showError && address.isBlank()
        )

        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Ville *") },
            modifier = Modifier.fillMaxWidth(),
            isError = showError && city.isBlank()
        )

        if (showError) {
            Text(
                "Veuillez remplir tous les champs",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        // Récapitulatif
        Text("Récapitulatif", style = MaterialTheme.typography.titleMedium)
        Text("${cartItems.size} articles")
        Text("Total: ${String.format("%.2f", total)} €")

        Spacer(Modifier.weight(1f))

        Button(
            onClick = {
                if (name.isNotBlank() && phone.isNotBlank() && address.isNotBlank() &&
                    city.isNotBlank() && phone.all { it.isDigit() }) {
                    vm.placeOrder(
                        context = context,
                        name = name,
                        phone = phone,
                        address = address,
                        city = city,
                        total = total,
                        itemsCount = cartItems.size
                    ) {
                        navController.navigate("history") {
                            popUpTo("catalog") { inclusive = false }
                        }
                    }
                } else {
                    showError = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmer")
        }
    }
}