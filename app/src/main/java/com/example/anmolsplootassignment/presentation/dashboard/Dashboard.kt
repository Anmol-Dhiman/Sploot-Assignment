package com.example.anmolsplootassignment.presentation.dashboard

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.anmolsplootassignment.presentation.dashboard.components.AlertMessage
import com.example.anmolsplootassignment.presentation.dashboard.components.DropDownMenus
import com.example.anmolsplootassignment.presentation.dashboard.components.PlaceDetailsCard
import com.example.anmolsplootassignment.ui.theme.DashboarStatusBar
import com.example.anmolsplootassignment.utils.MultiplePermissionStateExt.hasAllPermissions
import com.example.anmolsplootassignment.utils.MultiplePermissionStateExt.isPermanentlyDenied
import com.example.anmolsplootassignment.utils.MultiplePermissionStateExt.rememberMultiplePermissionStateExt
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Dashboard(
    viewModel: DashboardViewModel = hiltViewModel(),
    openAppSettings: () -> Unit
) {


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
fun MapsScreen(
    viewModel: DashboardViewModel
) {

    val queryData by viewModel.queryDataResponse.collectAsState()
    val currentLocation by viewModel.currentLocation.collectAsState()


    val searchInput by viewModel.searchInput.collectAsState()
    val radiusRange by viewModel.radiusRange.collectAsState()
    val locationType by viewModel.locationType.collectAsState()
    val showDetailsCard by viewModel.showDetailsCard.collectAsState()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var selectedMarker by remember {
        mutableStateOf(LatLng(currentLocation.latitude, currentLocation.longitude))
    }

    LocationServices.getFusedLocationProviderClient(context).lastLocation.addOnSuccessListener {
        viewModel.updateCurrentLocation(it)
        selectedMarker = LatLng(currentLocation.latitude, currentLocation.longitude)
    }
    val cameraPosition: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(currentLocation.latitude, currentLocation.longitude), 8f
        )
    }

    var cardIndex by remember {
        mutableStateOf(0)
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                uiSettings = MapUiSettings(zoomControlsEnabled = false, compassEnabled = false),
                cameraPositionState = cameraPosition,
                onMapClick = {
                    viewModel.updateDetailsCardStatus(false)
                },

                ) {
                MapEffect(key1 = currentLocation, key2 = queryData, block = { map ->
                    map.setOnMapLoadedCallback {
                        map.clear()
                        val currentLocationMarker = map.addMarker(
                            MarkerOptions().position(
                                LatLng(
                                    currentLocation.latitude,
                                    currentLocation.longitude
                                )
                            ).title("You are here")
                        )
                        currentLocationMarker?.showInfoWindow()
                        currentLocationMarker?.tag = "current location"

                        map.animateCamera(
                            CameraUpdateFactory.newCameraPosition(
                                CameraPosition.Builder()
                                    .target(selectedMarker)
                                    .zoom(15f).build()
                            )
                        )


                        if (queryData.status == "OK") {
                            queryData.results.forEachIndexed { index, it ->
                                val location = map.addMarker(
                                    MarkerOptions().position(
                                        LatLng(
                                            it.geometry?.location?.lat!!,
                                            it.geometry?.location?.lng!!
                                        )
                                    ).title(it.name?.capitalize(Locale.current))
                                        .icon(
                                            BitmapDescriptorFactory.defaultMarker(
                                                BitmapDescriptorFactory.HUE_ORANGE
                                            )
                                        )
                                )

                                location!!.tag = index

                            }

                        } else if (queryData.status == "UNKNOWN_ERROR" || queryData.status == "ZERO_RESULTS" || queryData.status == "REQUEST_DENIED") {
                            Toast.makeText(
                                context,
                                "Search data is not correct",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        map.setOnMarkerClickListener { marker ->
                            if (marker.tag != "current location") {
                                cardIndex = marker.tag as Int
                                viewModel.updateDetailsCardStatus(true)
                                marker.showInfoWindow()
                                val location = queryData.results[cardIndex].geometry!!.location
                                selectedMarker = LatLng(location?.lat!!, location?.lng!!)
                            } else {
                                marker.showInfoWindow()
                                selectedMarker =
                                    LatLng(currentLocation.latitude, currentLocation.longitude)
                            }

                            true
                        }
                    }
                })
            }


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
                        }
                        .border(
                            width = 0.5.dp,
                            color = Color.Gray,
                            shape = MaterialTheme.shapes.large
                        ),

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
                onRadiusChange = {
                    viewModel.updateRadiusRange(it)
                    if (searchInput.isNotEmpty() && locationType.isNotEmpty())
                        viewModel.fetchRequestedData()
                },
                onLocationTypeChange = {
                    viewModel.updateLocationType(it)
                    if (searchInput.isNotEmpty() && radiusRange.isNotEmpty())
                        viewModel.fetchRequestedData()


                }
            )
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            if (showDetailsCard) {
                val data = queryData.results[cardIndex]
                PlaceDetailsCard(
                    locationName = data.name!!,
                    rating = data.rating.toString(),
                    noOfReview = data.userRatingsTotal.toString(),
                    address = data.vicinity!!,
                    onDirectionClick = {

                        val gmmIntentUri =
                            Uri.parse("http://maps.google.com/maps?saddr=" + currentLocation.latitude.toString() + "," + currentLocation.longitude.toString() + "&daddr=" + data.geometry?.location?.lat.toString() + "," + data.geometry?.location?.lng.toString())
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        context.startActivity(mapIntent)
                    },
                    types = data.types,
                    businessStatus = data.businessStatus!!
                )
            }
        }

    }
}
