package com.example.anmolsplootassignment.presentation.dashboard


import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anmolsplootassignment.BuildConfig
import com.example.anmolsplootassignment.data.NearbySearchApiResponse
import com.example.anmolsplootassignment.repository.NearbySearchApi
import com.example.anmolsplootassignment.utils.Constants.NEARBY_SEARCH_BASE_URL
import com.example.anmolsplootassignment.utils.RetrofitBuilder.getRetrofitBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {


    private val _searchInput = MutableStateFlow("")
    val searchInput: StateFlow<String> = _searchInput

    private val _radiusRange = MutableStateFlow("")
    val radiusRange: StateFlow<String> = _radiusRange

    private val _locationType = MutableStateFlow("")
    val locationType: StateFlow<String> = _locationType

    private val _showDetailsCard = MutableStateFlow(false)
    val showDetailsCard: StateFlow<Boolean> = _showDetailsCard

    private val _queryDataResponse = MutableStateFlow(NearbySearchApiResponse())
    val queryDataResponse: StateFlow<NearbySearchApiResponse> = _queryDataResponse

    private val _currentLocation = MutableStateFlow(Location("service Provider"))
    val currentLocation: StateFlow<Location> = _currentLocation

    init {
        _currentLocation.value.latitude = 28.457449973862865
        _currentLocation.value.longitude = 77.04880826136397
    }

    fun updateCurrentLocation(it: Location) {
        _currentLocation.value.latitude = it.latitude
        _currentLocation.value.longitude = it.longitude
    }

    fun updateSearchInputValue(it: String) {
        _searchInput.value = it
    }

    fun updateDetailsCardStatus(it: Boolean) {
        _showDetailsCard.value = it
    }

    fun updateRadiusRange(it: String) {
        _radiusRange.value = it
    }


    fun updateLocationType(it: String) {
        _locationType.value = it
    }

    fun fetchRequestedData() {
        viewModelScope.launch {

            val nearbySearchApi =
                getRetrofitBuilder(NEARBY_SEARCH_BASE_URL).create(NearbySearchApi::class.java)

            try {
                nearbySearchApi.fetchNearbyLocation(
                    location = _currentLocation.value.latitude.toString() + "," + _currentLocation.value.longitude.toString(),
                    radius = _radiusRange.value,
                    type = _locationType.value,
                    keyword = _searchInput.value,
                    key = BuildConfig.MAPS_API_KEY
                ).enqueue(
                    object : Callback<NearbySearchApiResponse> {
                        override fun onResponse(
                            call: Call<NearbySearchApiResponse>,
                            response: Response<NearbySearchApiResponse>
                        ) {
                            Log.d("response", response.body().toString())
                            if (response.isSuccessful)
                                _queryDataResponse.value = response.body()!!

                        }

                        override fun onFailure(call: Call<NearbySearchApiResponse>, t: Throwable) {
                            Log.d("response", "failed")
                        }

                    })
            } catch (e: Exception) {

            }
        }
    }
}