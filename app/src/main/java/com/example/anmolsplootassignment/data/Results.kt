package com.example.anmolsplootassignment.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Results(

    @SerializedName("business_status") @Expose var businessStatus: String? = null,
    @SerializedName("geometry") @Expose var geometry: Geometry? = Geometry(),
    @SerializedName("name") @Expose var name: String? = null,
    @SerializedName("opening_hours") @Expose var openingHours: OpeningHours? = OpeningHours(),
    @SerializedName("rating") @Expose var rating: Float? = null,
    @SerializedName("types") @Expose var types: ArrayList<String> = arrayListOf(),
    @SerializedName("user_ratings_total") @Expose var userRatingsTotal: Int? = null,
    @SerializedName("vicinity") @Expose var vicinity: String? = null

)