package com.example.anmolsplootassignment.utils

import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState


object MultiplePermissionStateExt {


    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun rememberMultiplePermissionStateExt(permissions: List<String>): MultiplePermissionsState {
        return rememberMultiplePermissionsState(permissions = permissions)
    }

    @OptIn(ExperimentalPermissionsApi::class)
    fun MultiplePermissionsState.isPermanentlyDenied(): Boolean {
        permissions.forEach { perm ->
//            this means permanently denial of a permission
            val _status = !perm.shouldShowRationale && !perm.hasPermission
//            if a single permission is not permanently denied then we return false
            if (!_status)
                return _status
        }
        return true
    }

    @OptIn(ExperimentalPermissionsApi::class)
    fun MultiplePermissionsState.hasAllPermissions(): Boolean {
        permissions.forEach { perm ->
            val _status = perm.hasPermission
            if (!_status)
                return _status
        }
        return true
    }


}
