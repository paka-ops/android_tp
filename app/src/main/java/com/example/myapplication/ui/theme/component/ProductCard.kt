package com.example.myapplication.ui.theme.component
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.data.Product

// Débutant : widget réutilisable mais avec des magic numbers
@Composable
fun ProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp) // Magic number
            .fillMaxWidth()
            .height(250.dp) // Hardcodé
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            AsyncImage(
                model = product.image,
                contentDescription = null, // Débutant : oublie l'accessibilité
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = product.title,
                    maxLines = 2,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${product.price} €",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = product.category,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
