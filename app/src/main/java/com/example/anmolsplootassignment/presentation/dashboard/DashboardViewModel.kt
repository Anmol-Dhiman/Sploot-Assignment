package com.example.anmolsplootassignment.presentation.dashboard

import androidx.lifecycle.ViewModel
import com.example.anmolsplootassignment.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    dataCenter: DataRepository
) : ViewModel() {

    private val _searchInput = MutableStateFlow("")
    val searchInput: StateFlow<String> = _searchInput

    private val _radiusRange = MutableStateFlow("")
    val radiusRange: StateFlow<String> = _radiusRange


    private val _locationType = MutableStateFlow("")
    val locationType: StateFlow<String> = _locationType


    fun updateSearchInputValue(it: String) {
        _searchInput.value = it
    }

    fun updateRadiusRange(it: String) {
        _radiusRange.value = it
    }


    fun updateLocationType(it: String) {
        _locationType.value = it
    }

    fun fetchRequestedData() {

    }
}