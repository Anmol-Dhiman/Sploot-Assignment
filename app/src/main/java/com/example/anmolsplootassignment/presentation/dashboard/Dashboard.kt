package com.example.anmolsplootassignment.presentation.dashboard

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.anmolsplootassignment.presentation.dashboard.components.AlertMessage
import com.example.anmolsplootassignment.utils.MultiplePermissionStateExt.hasAllPermissions
import com.example.anmolsplootassignment.utils.MultiplePermissionStateExt.isPermanentlyDenied
import com.example.anmolsplootassignment.utils.MultiplePermissionStateExt.rememberMultiplePermissionStateExt
import com.google.accompanist.permissions.ExperimentalPermissionsApi


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Dashboard(viewModel: DashboardViewModel, openAppSettings: () -> Unit) {
    val permissionState = rememberMultiplePermissionStateExt(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    if (!permissionState.hasAllPermissions()) {
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
        Text(text = "hello world")
    }


}
