package com.example.myapplication

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication.ui.theme.HistoryScreen
import com.example.myapplication.ui.theme.ProfileScreen
import com.example.myapplication.ui.theme.cart.CartScreen
import com.example.myapplication.ui.theme.catalogue.CatalogScreen
import com.example.myapplication.ui.theme.detail.DetailScreen
import com.example.myapplication.ui.theme.order.OrderScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier // On ajoute le paramètre modifier ici
) {
    NavHost(
        navController = navController,
        startDestination = "catalog",
        modifier = modifier // On applique le modifier (qui contient le padding du Scaffold)
    ) {
        composable("catalog") { CatalogScreen(navController) }

        composable(
            "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStack ->
            val id = backStack.arguments?.getInt("id") ?: 0
            DetailScreen(id, navController)
        }

        composable("cart") { CartScreen(navController) }
        composable("checkout") { OrderScreen(navController) }
        composable("history") { HistoryScreen() }
        composable("profile") { ProfileScreen() }
    }
}