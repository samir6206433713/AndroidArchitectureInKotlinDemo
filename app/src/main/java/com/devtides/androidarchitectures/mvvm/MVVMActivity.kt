package com.devtides.androidarchitectures.mvvm

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import com.devtides.androidarchitectures.R
import com.devtides.androidarchitectures.mvvm.MVVMActivity

import java.util.*

class MVVMActivity : AppCompatActivity() {
    private val listValues: MutableList<String?>? = ArrayList()
    private var adapter: ArrayAdapter<String?>? = null
    private var list: ListView? = null
    private var viewModel: CountriesViewModel? = null
    private var retryButton: Button? = null
    private var progress: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm)
        title = "MVVM Activity"
        viewModel = ViewModelProviders.of(this).get(CountriesViewModel::class.java)
        list = findViewById<ListView?>(R.id.list)
        retryButton = findViewById<Button?>(R.id.retryButton)
        progress = findViewById<ProgressBar?>(R.id.progress)
        adapter = ArrayAdapter(this, R.layout.row_layout, R.id.listText, listValues)
        list?.setAdapter(adapter)
        list?.setOnItemClickListener(OnItemClickListener { parent, view, position, id -> Toast.makeText(this@MVVMActivity, "You clicked " + listValues?.get(position), Toast.LENGTH_SHORT).show() })
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel?.getCountries()?.observe(this, Observer { countries: MutableList<String?>? ->
            if (countries != null) {
                listValues?.clear()
                listValues?.addAll(countries)
                list?.setVisibility(View.VISIBLE)
                adapter?.notifyDataSetChanged()
            } else {
                list?.setVisibility(View.GONE)
            }
        })
        viewModel?.getCountryError()?.observe(this, Observer { error: Boolean? ->
            progress?.setVisibility(View.GONE)
            if (error!!) {
                Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
                retryButton?.setVisibility(View.VISIBLE)
            } else {
                retryButton?.setVisibility(View.GONE)
            }
        })
    }

    fun onRetry(view: View?) {
        viewModel?.onRefresh()
        list?.setVisibility(View.GONE)
        retryButton?.setVisibility(View.GONE)
        progress?.setVisibility(View.VISIBLE)
    }

    companion object {
        fun getIntent(context: Context?): Intent? {
            return Intent(context, MVVMActivity::class.java)
        }
    }
}