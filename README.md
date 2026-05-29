# PAKA - Application Mobile E-commerce

## 👤 Auteur(s)
**PAKA Essosolim Joel**

---

## 🛠 Technologie Utilisée
**Kotlin avec Jetpack Compose**

---

## 📱 Description de l'Application
PAKA est une application mobile de commerce électronique développée en Kotlin utilisant le framework Jetpack Compose. L'application permet aux utilisateurs de :
- Parcourir un catalogue de produits
- Consulter les détails des produits
- Ajouter/supprimer des articles du panier
- Gérer leur panier d'achat
- Finaliser leur commande

L'application offre une interface utilisateur moderne et réactive avec une excellente expérience utilisateur.

---

## ✅ Fonctionnalités Implémentées

- ✅ Affichage d'une liste de produits
- ✅ Détails du produit (description, prix, images)
- ✅ Système de panier d'achat
- ✅ Ajout/suppression de produits au panier
- ✅ Calcul du total du panier
- ✅ Écran de paiement/commande
- ✅ Interface utilisateur avec Jetpack Compose
- ✅ Navigation fluide entre les écrans
- ✅ Gestion d'état avec ViewModel
- ✅ Persistance des données (base de données locale)

---

## 📚 Bibliothèques Utilisées

| Bibliothèque | Version | Utilisation |
|---|---|---|
| Jetpack Compose | 1.5.x | Interface utilisateur moderne |
| Kotlin | 1.9.x | Langage principal |
| Android Architecture Components | Latest | ViewModel, LiveData |
| Hilt | 2.46 | Injection de dépendances |
| Room | 2.5.x | Base de données locale |
| Retrofit | 2.9.x | Requêtes API |
| Coil | 2.4.x | Chargement d'images |
| Material Design 3 | 1.0.x | Composants UI |

---

## 📸 Captures d'Écran

### Écran 1 - Liste des Produits

<img width="540" height="1230" alt="image" src="https://github.com/user-attachments/assets/90fc7d52-c16f-49dd-8495-7bf24467aada" />


### Écran 2 - Détails du Produit

<img width="540" height="1230" alt="image" src="https://github.com/user-attachments/assets/5c8c9266-dc04-45c2-aabc-5765272cc893" />


### Écran 3 - Panier d'Achat
<img width="1080" height="2460" alt="image" src="https://github.com/user-attachments/assets/f706e359-fbd4-4c3e-8132-116e210831d9" />



## 🔧 Difficultés Rencontrées et Solutions

Durant le développement de cette application, la principale difficulté a été la **gestion complexe de l'état avec Compose** et l'intégration de la **persistance des données avec Room**. L'état du panier devait rester synchronisé entre plusieurs écrans, ce qui a initialement créé des bugs d'affichage. La solution adoptée a été d'implémenter un **ViewModel centralisé** qui gère l'état global du panier via des `StateFlow`, et d'intégrer **Room Database** pour persister les données localement. Cela a permis une synchronisation fluide entre les écrans et une meilleure réactivité de l'interface.

---

## 💡 Améliorations Possibles

Si nous disposions de plus de temps, nous pourrions implémenter : (1) un **système d'authentification utilisateur complet** avec connexion/inscription et sauvegarde du profil, (2) une **intégration avec une API backend réelle** pour charger les produits dynamiquement, (3) un **système de notifications push** pour informer les utilisateurs des offres spéciales et mises à jour de commandes, (4) un **mode sombre/clair** avec sauvegarde des préférences utilisateur
---

## 🔗 Version Alternative

Cette application est également disponible en **Flutter** :
👉 [Voir la version Flutter/Dart](https://github.com/paka-ops/flutter_tp)

