package com.example.littlelemonfinalnc

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.AccountCircle

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
// Make sure this matches your package name!
import com.example.littlelemonfinalnc.model.MenuDatabase
import com.example.littlelemonfinalnc.model.MenuItemRoom
// Make sure to add this import at the top of your Home.kt file!
import coil.compose.AsyncImage

@Composable
fun HomeScreen(navController: NavHostController) {
    // 1. Get access to the database
    val context = LocalContext.current
    val database = MenuDatabase.getInstance(context).menuItemDao()

    // 2. Read the database.
    // observeAsState automatically updates the UI if the database changes!
    val menuItems by database.getAll().observeAsState(emptyList())

    Column(modifier = Modifier.fillMaxSize()) {

        // --- HEADER (From Chapter 8) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Little Lemon Logo",
                modifier = Modifier.size(50.dp),
                tint = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.weight(0.5f))

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                modifier = Modifier
                    .size(50.dp)
                    .clickable { navController.navigate("Profile") },
                tint = MaterialTheme.colorScheme.primary
            )
        }

        // --- MENU LIST ---
        Text(
            text = "Menu Items:",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        // 3. LazyColumn creates a scrollable list of food!
        LazyColumn {
            items(menuItems) { item ->
                MenuItemRow(item = item)
            }
        }
    }
}



@Composable
fun MenuItemRow(item: MenuItemRoom) {
    Divider() // A subtle line separating each menu item

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically // Aligns the text and image nicely
    ) {
        // The Text Column (Takes up available space)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp) // Adds space between the text and the image
        ) {
            Text(text = item.title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
            Text(text = "$${item.price}", style = MaterialTheme.typography.bodyLarge)
        }

        // The Image (Loaded from the internet URL)
        AsyncImage(
            model = item.imageUrl,
            contentDescription = "${item.title} Image",
            modifier = Modifier.size(80.dp)
        )
    }
}



/*
// 4. A helper composable to design how each individual food item looks
@Composable
fun MenuItemRow(item: MenuItemRoom) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = item.title, style = MaterialTheme.typography.titleMedium)
        Text(text = item.description, style = MaterialTheme.typography.bodyMedium)
        Text(text = "$${item.price}", style = MaterialTheme.typography.bodyLarge)
        Divider(modifier = Modifier.padding(top = 8.dp)) // A subtle line between items
    }
}
*/









/*
@Composable
fun HomeScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {

        // The Home Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f)) // Spacer to help center the logo

            // Center Logo
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Little Lemon Logo",
                modifier = Modifier.size(50.dp),
                tint = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.weight(0.5f)) // Spacer between logo and profile icon

            // Profile Icon (Clickable)
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        navController.navigate("Profile")
                    },
                tint = MaterialTheme.colorScheme.primary
            )
        }

        // Placeholder for your future menu list!
        Text(
            text = "Welcome to the Home Screen!",
            modifier = Modifier.padding(16.dp)
        )
    }
}


 */