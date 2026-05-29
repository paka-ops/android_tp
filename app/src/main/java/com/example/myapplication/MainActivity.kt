package com.example.myapplication

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           MyApplicationTheme {
                val navController = rememberNavController()
// Items de la bottom bar (débutant : hardcodé ici)
                val items = listOf(
                        "catalog" to Icons.Default.Home to "Produits",
                "cart" to Icons.Default.ShoppingCart to "Panier",
                "history" to Icons.Default.List to "Historique",
                "profile" to Icons.Default.Person to "Profil"
                )
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            items.forEach { (pair, label) ->
                                val (route, icon) = pair
                                NavigationBarItem(
                                    icon = { Icon(icon, contentDescription = null) },
                                    label = { Text(label) },
                                    selected = currentDestination?.hierarchy?.any {
                                        it.route?.startsWith(route) == true
                                    } == true,
                                    onClick = {
                                        navController.navigate(route) {
                                            popUpTo("catalog") { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding) // Débutant : gère mal les paddings parfois
                    )
                }
            }
        }
    }
}