package com.example.anmolsplootassignment.presentation.dashboard

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import com.example.anmolsplootassignment.presentation.dashboard.components.Instructions
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalPermissionsApi
@Composable
fun Dashboard() {

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )


    val onInstructionsClick = {
//TODO set the shared preference here
    }
    Instructions(onInstructionsClick)

}