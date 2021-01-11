package com.devtides.androidarchitectures.model


import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountriesService {
    private val api: CountriesApi?
    fun getCountries(): Single<MutableList<Country?>?>? {
        return api?.getCountries()
    }

    companion object {
        val BASE_URL: String? = "https://restcountries.eu/rest/v2/"
    }

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        api = retrofit.create(CountriesApi::class.java)
    }
}