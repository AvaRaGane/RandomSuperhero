package com.example.randomsuperhero

import com.google.gson.annotations.SerializedName

data class Biography(
    val publisher: String,
    val alignment: String,
    @SerializedName("first-appearance") val firstAppearance: String?
)