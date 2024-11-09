package com.bush.littlelemoncappro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.bush.littlelemoncappro.data.Database
import com.bush.littlelemoncappro.data.MenuItemNetwork
import com.bush.littlelemoncappro.data.MenuNetwork
import com.bush.littlelemoncappro.navigation.Navigation
import com.bush.littlelemoncappro.ui.theme.LittleLemonCapProTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }
    val database by lazy {
        Room.databaseBuilder(applicationContext, Database::class.java, "database").build()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonCapProTheme {
                val navController = rememberNavController()
                val menuItems by database.menuItemDao().getAll().observeAsState(emptyList())
                Navigation(navController, menuItems)
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            if (database.menuItemDao().isEmpty()) {
                database.menuItemDao().deleteAll()
                saveMenuItems(getMenuItems())
            }
        }
    }

    private suspend fun getMenuItems(): List<MenuItemNetwork> {
        val response = httpClient.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body<MenuNetwork>()
        return response.menu
    }

    private fun saveMenuItems(items: List<MenuItemNetwork>) {
        val localItemList = items.map {
            it.convert()
        }
        database.menuItemDao().insertAll(localItemList)
    }
}