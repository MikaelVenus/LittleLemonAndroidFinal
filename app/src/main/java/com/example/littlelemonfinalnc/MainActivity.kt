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
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Important Ktor imports:
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
//import com.redravencomputing.littlelemonandroid.model.* // Import your models
import com.example.littlelemonfinalnc.model.*


/*

class MainActivity : ComponentActivity() {
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
}

 */


/*
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

/*
*/


 */



class MainActivity : ComponentActivity() {

    // 1. Build the Ktor HTTP Client
    /*private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }*/

    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            // This setup is much more forgiving and won't crash easily
            json(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            },
                contentType = ContentType("text", "plain")
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 2. Launch background thread to fetch data immediately upon app launch
        lifecycleScope.launch(Dispatchers.IO) {
            val database = MenuDatabase.getInstance(applicationContext).menuItemDao()

            try {
                val menuItems = fetchMenu()
                if (!database.isEmpty()) {
                    database.deleteAll()
                }
                saveMenuToDatabase(menuItems, database)
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching menu: ${e.message}")
            }
        }

        // 3. Your perfectly working UI and Navigation logic
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // Open SharedPreferences
                    val sharedPreferences = getSharedPreferences("LittleLemonPrefs", Context.MODE_PRIVATE)

                    // Check the login status
                    val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

                    // Decide the starting screen
                    val startScreen = if (isLoggedIn) Home.route else Onboarding.route

                    // Launch the Navigation
                    MyNavigation(navController = navController, startDestination = startScreen)
                }
            }
        }
    }

    // 4. Function to grab the JSON from the URL
    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        val url = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
        val response: MenuNetworkData = httpClient.get(url).body()
        return response.menu
    }

    // 5. Function to convert network items and insert them into the database
    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>, database: MenuItemDao) {
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        database.insertAll(menuItemsRoom)
    }
}