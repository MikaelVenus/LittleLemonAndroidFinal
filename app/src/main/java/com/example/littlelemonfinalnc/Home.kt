package com.example.littlelemonfinalnc

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {

        // Add this button to jump to the Profile screen
        Button(onClick = { navController.navigate(Profile.route) }) {
            Text("Go to Profile")
        }

        Text(text = "Welcome to the Home Screen!")
    }
}