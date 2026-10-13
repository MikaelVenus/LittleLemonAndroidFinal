package com.example.littlelemonfinalnc

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import android.widget.Toast
import androidx.navigation.NavHostController


import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(navController: NavHostController) {
    val context = LocalContext.current

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

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
            // Replace with Image() if you have the logo drawable
            Text(
                text = "Little Lemon",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF495E57) // Brand Green
            )
        }

        // --- WELCOME BANNER ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF495E57)) // Brand Green
                .padding(vertical = 40.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Let's get to know you",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }

        // --- FORM SECTION ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = "Personal Information",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = "First Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Last Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.weight(1f)) // Pushes the button to the bottom

            // --- REGISTER BUTTON ---
            Button(
                onClick = {
                    if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                        Toast.makeText(context, "Registration unsuccessful. Please enter all data.", Toast.LENGTH_SHORT).show()
                    } else if (!isValidEmail(email)) {
                        Toast.makeText(context, "Please enter a valid email address.", Toast.LENGTH_SHORT).show()
                    } else {
                        saveUserData(context, firstName, lastName, email)
                        navController.navigate("Home")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14)), // Brand Yellow
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Register",
                    color = Color(0xFF495E57),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
