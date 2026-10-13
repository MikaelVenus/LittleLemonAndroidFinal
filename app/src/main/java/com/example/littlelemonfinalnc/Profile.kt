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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle



// import com.redravencomputing.littlelemonandroid.R // Make sure this matches your package name!

@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("LittleLemonPrefs", Context.MODE_PRIVATE)

    // Retrieve the saved data
    val firstName = sharedPreferences.getString("firstName", "N/A")
    val lastName = sharedPreferences.getString("lastName", "N/A")
    val email = sharedPreferences.getString("email", "N/A")

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Step 1: The Header
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Profile Icon Placeholder",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        // Step 2: Display User Data
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Profile information:",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(text = "First name: $firstName")
            Spacer(modifier = Modifier.height(15.dp))

            Text(text = "Last name: $lastName")
            Spacer(modifier = Modifier.height(15.dp))

            Text(text = "Email: $email")
        }

        Spacer(modifier = Modifier.weight(1f)) // Pushes the button to the bottom

        // Step 3: The Log Out Button (Next section)
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
                .padding(20.dp)
        ) {
            Text("Log out")
        }
    }
}