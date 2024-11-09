package com.bush.littlelemoncappro.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bush.littlelemoncappro.data.MenuItem
import com.bush.littlelemoncappro.data.PreferencesKeys
import com.bush.littlelemoncappro.screens.HomeScreen
import com.bush.littlelemoncappro.screens.OnboardingScreen
import com.bush.littlelemoncappro.screens.ProfileScreen

@Composable
fun Navigation(navHostController: NavHostController, menuItems: List<MenuItem>) {
    val sharedPreferences =
        LocalContext.current.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)
    val userLoggedIn = sharedPreferences.getBoolean(PreferencesKeys.IS_LOGGED_IN, false)
    val startDestination = if (userLoggedIn) Home.route else Onboarding.route

    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(Home.route) {
            HomeScreen(navHostController, menuItems)
        }
        composable(Onboarding.route) {
            OnboardingScreen(navHostController)
        }
        composable(Profile.route) {
            ProfileScreen(navHostController)
        }
    }
}