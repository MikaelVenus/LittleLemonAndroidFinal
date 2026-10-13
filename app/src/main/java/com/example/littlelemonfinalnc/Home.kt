package com.example.littlelemonfinalnc

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier

import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.AccountCircle

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext

import com.example.littlelemonfinalnc.model.MenuDatabase
import com.example.littlelemonfinalnc.model.MenuItemRoom
import coil.compose.AsyncImage

import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults



@Composable
fun HomeScreen(navController: NavHostController) {
    // 1. Get access to the database
    val context = LocalContext.current
    val database = MenuDatabase.getInstance(context).menuItemDao()
    val menuItems by database.getAll().observeAsState(emptyList())

    // 1. Remember the user's search text
    var searchPhrase by remember { mutableStateOf("") }
    var sortAZ by remember { mutableStateOf(false) } // <-- ADD THIS

    // 2. Filter and Sort the list dynamically
    var filteredMenuItems = menuItems

    // If the user typed something, filter the list
    if (searchPhrase.isNotEmpty()) {
        filteredMenuItems = filteredMenuItems.filter {
            it.title.contains(searchPhrase, ignoreCase = true)
        }
    }

    // Always sort the final result alphabetically
    // Only sort A-Z if the button was clicked
    if (sortAZ) {
        filteredMenuItems = filteredMenuItems.sortedBy { it.title }
    }

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
                //tint = MaterialTheme.colorScheme.secondary
                tint = MaterialTheme.colorScheme.secondary // Replaces hardcoded Yellow
            )

            Spacer(modifier = Modifier.weight(0.5f))

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                modifier = Modifier
                    .size(50.dp)
                    .clickable { navController.navigate("Profile") },
                //tint = MaterialTheme.colorScheme.secondary
                tint = MaterialTheme.colorScheme.secondary // Replaces hardcoded Yellow
            )
        }

        // --- HERO SECTION WITH SEARCH BAR (Added Here!) ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                // Replaces hardcoded Green
                .background(MaterialTheme.colorScheme.primary)
                //.background(Color(0xFF495E57))
                .padding(16.dp)
        ) {
            Text(text = "Little Lemon",
                //color = Color(0xFFF4CE14),
                // Replaces hardcoded Green
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold)

            Text(text = "Chicago", color = Color.White, fontSize = 24.sp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                    color = Color.White,
                    modifier = Modifier
                        .weight(0.6f)
                        .padding(end = 8.dp),
                    fontSize = 16.sp
                )

            }

            // 3. The Search Bar Input Field
            OutlinedTextField(
                value = searchPhrase,
                onValueChange = { searchPhrase = it },
                placeholder = { Text("Enter Search Phrase") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
        }

        // --- MENU LIST ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Menu Items:",
                style = MaterialTheme.typography.titleLarge

            )

            // The Sorting Button
            Button(
                onClick = { sortAZ = !sortAZ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14))
            ) {
                // Change the button text based on whether it is currently sorted
                Text(
                    text = if (sortAZ) "Clear Sort" else "Sort A-Z",
                    //color = Color(0xFF495E57)
                    // Replaces hardcoded Green
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // 3. LazyColumn creates a scrollable list of food!
        LazyColumn {
            // Updated to use the filtered list so the search actually works!
            items(filteredMenuItems) { item ->
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