package com.example.anmolsplootassignment.utils.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.anmolsplootassignment.presentation.dashboard.Dashboard
import com.example.anmolsplootassignment.presentation.dashboard.DashboardViewModel
import com.example.anmolsplootassignment.presentation.dashboard.components.Instructions
import com.example.anmolsplootassignment.presentation.starter.SplashScreen

@Composable
fun Navigation(
    modifier: Modifier,
    navController: NavHostController,
    onInstructionClick: () -> Unit,
    isFirstTime: Boolean,
    dashboardViewModel: DashboardViewModel,
    openAppSettings: () -> Unit
) {


    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController, isFirstTime = isFirstTime)
        }
        composable(Screen.Dashboard.route) {
            Dashboard(viewModel = dashboardViewModel, openAppSettings = openAppSettings)
        }
        composable(Screen.Instruction.route) {
            Instructions(onClick = onInstructionClick, navController = navController)
        }
    }

}