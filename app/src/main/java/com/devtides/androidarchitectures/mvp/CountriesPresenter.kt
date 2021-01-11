package com.devtides.androidarchitectures.mvp


import com.devtides.androidarchitectures.model.CountriesService
import com.devtides.androidarchitectures.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

import java.util.*

class CountriesPresenter(private val view: View?) {
    private val service: CountriesService?
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
                        view?.setValues(countryNames)
                    }

                    override fun onError(e: Throwable?) {
                        view?.onError()
                    }
                })
    }

    fun onRefresh() {
        fetchCountries()
    }

    interface View {
        open fun setValues(countries: MutableList<String?>?)
        open fun onError()
    }

    init {
        service = CountriesService()
        fetchCountries()
    }
}