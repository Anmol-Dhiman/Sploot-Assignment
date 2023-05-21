package com.example.anmolsplootassignment.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseData(@SerializedName("results") @Expose var result: ArrayList<Results> = arrayListOf())
