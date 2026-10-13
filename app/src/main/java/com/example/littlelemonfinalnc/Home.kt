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