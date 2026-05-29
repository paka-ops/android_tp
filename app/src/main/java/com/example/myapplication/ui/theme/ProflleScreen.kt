package com.example.myapplication.ui.theme



import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel : ViewModel() {
    // Débutant : pas de DataStore, juste des états en mémoire !
    var name = mutableStateOf("Jean Dupont")
    var email = mutableStateOf("jean@email.com")
    var phone = mutableStateOf("0601020304")
    var isDarkMode = mutableStateOf(false)

    fun clearData(context: Context, onCleared: () -> Unit) {
        // On lance une coroutine sur le Dispatcher IO (optimisé pour la base de données)
        CoroutineScope(Dispatchers.IO).launch {
            // 1. Action en arrière-plan (écriture/suppression DB)
            AppDatabase.getDatabase(context).cartDao().clearAll()

            // 2. Retour sur le thread principal pour mettre à jour l'interface (UI)
            withContext(Dispatchers.Main) {
                onCleared()
            }
        }
    }
}

@Composable
fun ProfileScreen(vm: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Profil", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = vm.name.value,
            onValueChange = { vm.name.value = it },
            label = { Text("Nom") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = vm.email.value,
            onValueChange = { vm.email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = vm.phone.value,
            onValueChange = { vm.phone.value = it },
            label = { Text("Téléphone") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = { /* Sauvegarde fictive */ }) {
            Text("Sauvegarder")
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Mode sombre")
            Switch(
                checked = vm.isDarkMode.value,
                onCheckedChange = { vm.isDarkMode.value = it }
            )
        }

        Spacer(Modifier.weight(1f))

        OutlinedButton(
            onClick = { showDialog = true },
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Vider mes données")
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmation") },
            text = { Text("Voulez-vous vraiment tout supprimer ?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        vm.clearData(context) {
                            showDialog = false
                        }
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Oui")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Annuler")
                }
            }
        )
    }
}