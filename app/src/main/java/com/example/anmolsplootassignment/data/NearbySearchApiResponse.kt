package com.example.anmolsplootassignment.data


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class NearbySearchApiResponse(
    @SerializedName("results") @Expose var results: ArrayList<Results> = arrayListOf()
)

