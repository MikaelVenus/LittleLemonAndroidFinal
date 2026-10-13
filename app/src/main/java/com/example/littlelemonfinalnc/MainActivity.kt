package com.example.littlelemonfinalnc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.littlelemonfinalnc.ui.theme.LittleLemonFinalNCTheme

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


/* class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LittleLemonFinalNCTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 1. Initialize the Navigation Controller
                    val navController = rememberNavController()

                    // 2. Check SharedPreferences to see if the user is already logged in
                    val sharedPreferences = getSharedPreferences("LittleLemonPrefs", Context.MODE_PRIVATE)
                    val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

                    // 3. Decide which screen to show first based on login status
                    val startScreen = if (isLoggedIn) "Home" else "Onboarding"

                    // 4. Create the Navigation Host (the map of your app)
                    NavHost(navController = navController, startDestination = startScreen) {

                        // Define the Onboarding Route
                        composable("Onboarding") {
                            OnboardingScreen(navController = navController)
                        }

                        // Define the Home Route (You will build this screen next)
                        composable("Home") {
                            // HomeScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}\


 */


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // 1. Open SharedPreferences
                    val sharedPreferences = getSharedPreferences("LittleLemonPrefs", Context.MODE_PRIVATE)

                    // 2. Check the login status (defaults to false if it doesn't exist)
                    val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

                    // 3. Decide the starting screen
                    val startScreen = if (isLoggedIn) Home.route else Onboarding.route

                    // 4. Launch the Navigation
                    MyNavigation(navController = navController, startDestination = startScreen)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LittleLemonFinalNCTheme {
        Greeting("Android")
    }
}