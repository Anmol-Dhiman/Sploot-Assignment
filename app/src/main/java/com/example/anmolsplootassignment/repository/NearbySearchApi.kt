package com.example.anmolsplootassignment.repository

import com.example.anmolsplootassignment.data.NearbySearchApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NearbySearchApi {


    @GET("/maps/api/place/nearbysearch/json")
    fun fetchNearbyLocation(
        @Query("location", encoded = true) location: String,
        @Query("radius") radius: String,
        @Query("type") type: String,
        @Query("keyword") keyword: String,
        @Query("key") key: String
    ): Call<NearbySearchApiResponse>

}