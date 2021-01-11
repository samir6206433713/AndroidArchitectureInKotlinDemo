package com.devtides.androidarchitectures.mvc

import android.content.Context
import android.content.Intent
import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import com.devtides.androidarchitectures.R
import com.devtides.androidarchitectures.mvc.MVCActivity

import java.util.*

class MVCActivity : AppCompatActivity() {
    private val listValues: MutableList<String?>? = ArrayList()
    private var adapter: ArrayAdapter<String?>? = null
    private var list: ListView? = null
    private var controller: CountriesController? = null
    private var retryButton: Button? = null
    private var progress: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvc)
        title = "MVC Activity"
        controller = CountriesController(this)
        list = findViewById<ListView?>(R.id.list)
        retryButton = findViewById<Button?>(R.id.retryButton)
        progress = findViewById<ProgressBar?>(R.id.progress)
        adapter = ArrayAdapter(this, R.layout.row_layout, R.id.listText, listValues)
        list?.setAdapter(adapter)
        list?.setOnItemClickListener(OnItemClickListener { parent, view, position, id -> Toast.makeText(this@MVCActivity, "You clicked " + listValues?.get(position), Toast.LENGTH_SHORT).show() })
    }

    fun setValues(values: MutableList<String?>?) {
        listValues?.clear()
        if (values != null) {
            listValues?.addAll(values)
        }
        retryButton?.setVisibility(View.GONE)
        progress?.setVisibility(View.GONE)
        list?.setVisibility(View.VISIBLE)
        adapter?.notifyDataSetChanged()
    }

    fun onRetry(view: View?) {
        controller?.onRefresh()
        list?.setVisibility(View.GONE)
        retryButton?.setVisibility(View.GONE)
        progress?.setVisibility(View.VISIBLE)
    }

    fun onError() {
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
        progress?.setVisibility(View.GONE)
        list?.setVisibility(View.GONE)
        retryButton?.setVisibility(View.VISIBLE)
    }

    companion object {
        fun getIntent(context: Context?): Intent? {
            return Intent(context, MVCActivity::class.java)
        }
    }
}