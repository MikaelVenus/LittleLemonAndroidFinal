package com.example.littlelemonfinalnc

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("LittleLemonPrefs", Context.MODE_PRIVATE)

    // Retrieve the saved data
    val firstName = sharedPreferences.getString("firstName", "N/A") ?: "N/A"
    val lastName = sharedPreferences.getString("lastName", "N/A") ?: "N/A"
    val email = sharedPreferences.getString("email", "N/A") ?: "N/A"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // --- BRANDING HEADER ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Little Lemon",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF495E57) // Brand Green
            )
        }

        // --- PROFILE ICON ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile Icon",
                modifier = Modifier
                    .size(100.dp),
                tint = Color(0xFF495E57) // Brand Green
            )
        }

        // --- USER DATA SECTION ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Profile Information",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = firstName,
                onValueChange = {},
                readOnly = true,
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = {},
                readOnly = true,
                label = { Text("Last Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {},
                readOnly = true,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f)) // Pushes the button to the bottom

        // --- LOG OUT BUTTON ---
        Button(
            onClick = {
                // Clear all SharedPreferences data
                sharedPreferences.edit().clear().apply()

                // Navigate back to Onboarding and clear the back-stack
                navController.navigate("Onboarding") {
                    popUpTo("Home") { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14)), // Brand Yellow
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Log Out",
                color = Color(0xFF495E57),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
