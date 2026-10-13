package com.example.littlelemonfinalnc

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MyNavigation(navController: NavHostController, startDestination: String) {
    // The NavHost controls which screen is currently visible
    NavHost(navController = navController, startDestination = startDestination) {

        // Route 1: Onboarding
        composable(Onboarding.route) {
            OnboardingScreen(navController = navController)
        }

        // Route 2: Home
        composable(Home.route) {
            HomeScreen(navController = navController)
        }

        // Route 3: Profile
        composable(Profile.route) {
            ProfileScreen(navController = navController)
        }
    }
}