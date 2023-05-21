package com.example.anmolsplootassignment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.anmolsplootassignment.ui.theme.AnmolSplootAssignmentTheme
import com.example.anmolsplootassignment.utils.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val isFirstTime = sharedPref.getString(getString(R.string.is_first_time), "yes")
        val editor = sharedPref.edit()


        val onInstructionClick = {
            editor.putString(getString(R.string.is_first_time), "no").apply()
        }

        setContent {
            AnmolSplootAssignmentTheme {
                navHostController = rememberNavController()
                Navigation(
                    modifier = Modifier,
                    navController = navHostController,
                    isFirstTime = isFirstTime == "yes",
                    onInstructionClick = onInstructionClick,
                    openAppSettings = ::openAppSettings
                )
            }
        }
    }
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

