package com.example.anmolsplootassignment.presentation.dashboard

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.anmolsplootassignment.presentation.dashboard.components.AlertMessage
import com.example.anmolsplootassignment.presentation.dashboard.components.DropDownMenus
import com.example.anmolsplootassignment.presentation.dashboard.components.PlaceDetailsCard
import com.example.anmolsplootassignment.ui.theme.DashboarStatusBar
import com.example.anmolsplootassignment.utils.MultiplePermissionStateExt.hasAllPermissions
import com.example.anmolsplootassignment.utils.MultiplePermissionStateExt.isPermanentlyDenied
import com.example.anmolsplootassignment.utils.MultiplePermissionStateExt.rememberMultiplePermissionStateExt
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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


@SuppressLint("MissingPermission")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MapsScreen(viewModel: DashboardViewModel) {


    val searchInput by viewModel.searchInput.collectAsState()
    val radiusRange by viewModel.radiusRange.collectAsState()
    val locationType by viewModel.locationType.collectAsState()
    val showDetailsCard by viewModel.showDetailsCard.collectAsState()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val queryData by viewModel.queryDataResponse.collectAsState()
    val currentLocation by viewModel.currentLocation.collectAsState()


    LocationServices.getFusedLocationProviderClient(context).lastLocation.addOnSuccessListener {

        viewModel.updateCurrentLocation(it)
    }




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
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            if (searchInput.isNotEmpty() && radiusRange.isNotEmpty() && locationType.isNotEmpty() && currentLocation.latitude != 0.0 && currentLocation.longitude != 0.0) {
                                viewModel.fetchRequestedData()

                            } else
                                Toast.makeText(
                                    context,
                                    "Each field is required!",
                                    Toast.LENGTH_SHORT
                                ).show()
                        }
                    ),

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .onFocusChanged {
                            if (it.hasFocus)
                                viewModel.updateDetailsCardStatus(false)
                        },

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
                            text = "ex : Cruise, Harbour, Airport...",
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


//        Box(Modifier.align(Alignment.BottomCenter)) {
//            if (showDetailsCard)
//                PlaceDetailsCard(
//                    locationName = "slajfkd",
//                    rating = "slkdjf",
//                    noOfReview = "aslkdf",
//                    address = "slkdjf",
//                    onDirectionClick = { /*TODO*/ },
//                    types = listOf("bar", "rest"),
//                    nearby = "slkdjf"
//                )
//        }

    }
}