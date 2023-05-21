package com.example.anmolsplootassignment.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Photos (
  @SerializedName("height"            )@Expose var height           : Int?              = null,
  @SerializedName("html_attributions" )@Expose var htmlAttributions : ArrayList<String> = arrayListOf(),
  @SerializedName("photo_reference"   )@Expose var photoReference   : String?           = null,
  @SerializedName("width"             )@Expose var width            : Int?              = null

)