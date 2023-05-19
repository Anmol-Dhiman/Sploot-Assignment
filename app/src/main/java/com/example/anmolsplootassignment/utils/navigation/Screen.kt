package com.example.anmolsplootassignment.utils.navigation

import com.example.anmolsplootassignment.utils.Constants.DASHBOARD
import com.example.anmolsplootassignment.utils.Constants.SPLASH_SCREEN


sealed class Screen(val route: String) {
    object SplashScreen : Screen(SPLASH_SCREEN)
    object Dashboard : Screen(DASHBOARD)

}
