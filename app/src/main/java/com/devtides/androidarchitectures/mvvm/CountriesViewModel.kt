package com.devtides.androidarchitectures.mvvm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import com.devtides.androidarchitectures.model.CountriesService
import com.devtides.androidarchitectures.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

import java.util.*

class CountriesViewModel : ViewModel() {
    private val countries: MutableLiveData<MutableList<String?>?>? = MutableLiveData()
    private val countryError: MutableLiveData<Boolean?>? = MutableLiveData()
    private val service: CountriesService?
    fun getCountries(): LiveData<MutableList<String?>?>? {
        return countries
    }

    fun getCountryError(): LiveData<Boolean?>? {
        return countryError
    }

    private fun fetchCountries() {
        service?.getCountries()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeWith(object : DisposableSingleObserver<MutableList<Country?>?>() {
                    override fun onSuccess(value: MutableList<Country?>?) {
                        val countryNames: MutableList<String?> = ArrayList()
                        for (country in value!!) {
                            countryNames.add(country?.countryName)
                        }
                        countries?.setValue(countryNames)
                        countryError?.setValue(false)
                    }

                    override fun onError(e: Throwable?) {
                        countryError?.setValue(true)
                    }
                })
    }

    fun onRefresh() {
        fetchCountries()
    }

    init {
        service = CountriesService()
        fetchCountries()
    }
}