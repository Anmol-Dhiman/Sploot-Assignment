package com.example.anmolsplootassignment.presentation.dashboard.components

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.anmolsplootassignment.utils.MultiplePermissionStateExt.hasAllPermissions
import com.example.anmolsplootassignment.utils.MultiplePermissionStateExt.rememberMultiplePermissionStateExt
import com.example.anmolsplootassignment.utils.navigation.Screen
import com.google.accompanist.permissions.ExperimentalPermissionsApi


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Instructions(navController: NavHostController, onClick: () -> Unit) {
    val permissionState = rememberMultiplePermissionStateExt(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 30.dp), contentAlignment = Alignment.Center
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(
                    MaterialTheme.colors.surface, shape = MaterialTheme.shapes.medium
                )
                .padding(vertical = 70.dp, horizontal = 60.dp)

        ) {
            _instruction(
                heading = "Step 1",
                content = "Select the catagory of locations which you want to search nearby"
            )
            _instruction(
                heading = "Step 2",
                content = "Select the radius inside which you want to search the location"
            )
            _instruction(
                heading = "Step 3",
                content = "Tap any red pin on the map to know more details about that location"
            )
            Spacer(modifier = Modifier.height(height = 25.dp))

            Button(
                onClick = {
                    onClick()
                    if (!permissionState.hasAllPermissions()) {
                        permissionState.launchMultiplePermissionRequest()
                        navController.popBackStack()
                        navController.navigate(Screen.Dashboard.route)
                    }
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.onBackground)
            ) {
                Text(text = "Got it", color = MaterialTheme.colors.surface)
            }
        }
    }


}

@Composable
fun _instruction(heading: String, content: String) {
    Text(
        text = heading, style = MaterialTheme.typography.h6, color = MaterialTheme.colors.onSurface
    )
    Spacer(modifier = Modifier.height(height = 25.dp))
    Text(
        text = content,
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.onSecondary,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(height = 25.dp))
}


