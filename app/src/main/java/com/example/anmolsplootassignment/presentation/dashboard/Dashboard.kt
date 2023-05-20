package com.example.anmolsplootassignment.presentation.dashboard

import android.Manifest
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.anmolsplootassignment.presentation.dashboard.components.AlertMessage
import com.example.anmolsplootassignment.presentation.dashboard.components.DropDownMenus
import com.example.anmolsplootassignment.ui.theme.DashboarStatusBar
import com.example.anmolsplootassignment.utils.MultiplePermissionStateExt.hasAllPermissions
import com.example.anmolsplootassignment.utils.MultiplePermissionStateExt.isPermanentlyDenied
import com.example.anmolsplootassignment.utils.MultiplePermissionStateExt.rememberMultiplePermissionStateExt
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Dashboard(viewModel: DashboardViewModel, openAppSettings: () -> Unit) {
    val permissionState = rememberMultiplePermissionStateExt(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val systemUiController =
        rememberSystemUiController()



    if (!permissionState.hasAllPermissions()) {
        systemUiController.setStatusBarColor(color = MaterialTheme.colors.surface)
        if (permissionState.isPermanentlyDenied()) {
            AlertMessage(
                message = "Go to app settings to give permission",
                buttonText = "Move",
                onClick = openAppSettings,
            )
        } else {
            AlertMessage(
                message = "Require location permission to provide service",
                buttonText = "Give Permission",
                onClick =
                { permissionState.launchMultiplePermissionRequest() },
            )

        }

    } else {
        systemUiController.setStatusBarColor(color = DashboarStatusBar)
        MapsScreen(viewModel = viewModel)
    }


}


@Composable
fun MapsScreen(viewModel: DashboardViewModel) {

    val searchInput by viewModel.searchInput.collectAsState()
    val radiusRange by viewModel.radiusRange.collectAsState()
    val locationType by viewModel.locationType.collectAsState()
    val context = LocalContext.current



    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                uiSettings = MapUiSettings(zoomControlsEnabled = false, compassEnabled = false),

                )
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 30.dp),
                contentAlignment = Alignment.Center
            ) {
                TextField(
                    value = searchInput,
                    onValueChange = { viewModel.updateSearchInputValue(it) },
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (searchInput.isNotEmpty() && radiusRange.isNotEmpty() && locationType.isNotEmpty())
                                viewModel.fetchRequestedData()
                            else
                                Toast.makeText(
                                    context,
                                    "Each field is required!",
                                    Toast.LENGTH_SHORT
                                ).show()
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),

                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Gray,
                        )
                    },
                    shape = MaterialTheme.shapes.large,


                    placeholder = {
                        Text(
                            text = "ex : Restaurants, Malls, Airport...",
                            color = Color.Gray,
                            style = MaterialTheme.typography.body2
                        )
                    }
                )

            }


            DropDownMenus(
                radiusRange = radiusRange,
                locationType = locationType,
                onRadiusChange = { viewModel.updateRadiusRange(it) },
                onLocationTypeChange = { viewModel.updateLocationType(it) }
            )
        }

    }
}