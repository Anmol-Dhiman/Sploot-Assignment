package com.example.anmolsplootassignment.presentation.dashboard

import androidx.lifecycle.ViewModel
import com.example.anmolsplootassignment.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    dataCenter: DataRepository
) : ViewModel() {


}