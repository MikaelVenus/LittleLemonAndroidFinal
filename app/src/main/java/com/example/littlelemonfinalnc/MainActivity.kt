package com.example.littlelemonfinalnc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.littlelemonfinalnc.ui.theme.LittleLemonFinalNCTheme

import android.content.Context
import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.lifecycleScope
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
import com.example.littlelemonfinalnc.model.*


class MainActivity : ComponentActivity() {

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
            // CHANGE THIS LINE: Use your custom theme instead of the default MaterialTheme
            LittleLemonFinalNCTheme {
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