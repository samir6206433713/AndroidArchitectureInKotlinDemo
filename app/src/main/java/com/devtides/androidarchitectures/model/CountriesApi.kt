package com.devtides.androidarchitectures.model


import io.reactivex.Single
import retrofit2.http.GET

interface CountriesApi {
    @GET("all")
    open fun getCountries(): Single<MutableList<Country?>?>?
}