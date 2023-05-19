package com.example.anmolsplootassignment.utils.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.anmolsplootassignment.presentation.dashboard.Dashboard
import com.example.anmolsplootassignment.presentation.starter.SplashScreen

@Composable
fun Navigation(
    modifier: Modifier,
    navController: NavHostController,

    ) {


    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(onNavigateToDashboard = { navController.navigate(Screen.Dashboard.route) })

        }
        composable(Screen.Dashboard.route) {
            Dashboard()
        }
    }

}