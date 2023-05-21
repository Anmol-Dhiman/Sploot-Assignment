package com.example.anmolsplootassignment.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Results(

    @SerializedName("business_status") @Expose var businessStatus: String? = null,
    @SerializedName("geometry") @Expose var geometry: Geometry? = Geometry(),
    @SerializedName("icon") @Expose var icon: String? = null,
    @SerializedName("icon_background_color") @Expose var iconBackgroundColor: String? = null,
    @SerializedName("icon_mask_base_uri") @Expose var iconMaskBaseUri: String? = null,
    @SerializedName("name") @Expose var name: String? = null,
    @SerializedName("opening_hours") @Expose var openingHours: OpeningHours? = OpeningHours(),
    @SerializedName("photos") @Expose var photos: ArrayList<Photos> = arrayListOf(),
    @SerializedName("place_id") @Expose var placeId: String? = null,
    @SerializedName("plus_code") @Expose var plusCode: PlusCode? = PlusCode(),
    @SerializedName("rating") @Expose var rating: Double? = null,
    @SerializedName("reference") @Expose var reference: String? = null,
    @SerializedName("scope") @Expose var scope: String? = null,
    @SerializedName("types") @Expose var types: ArrayList<String> = arrayListOf(),
    @SerializedName("user_ratings_total") @Expose var userRatingsTotal: Int? = null,
    @SerializedName("vicinity") @Expose var vicinity: String? = null

)