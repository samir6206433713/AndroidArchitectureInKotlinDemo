package com.devtides.androidarchitectures.model

import com.google.gson.annotations.SerializedName


class Country {
    @SerializedName("name")
    var countryName: String? = null
}