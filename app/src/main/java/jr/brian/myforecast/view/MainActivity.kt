package jr.brian.myforecast.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jr.brian.myforecast.R
import jr.brian.myforecast.databinding.ActivityMainBinding
import jr.brian.myforecast.model.remote.ForecastResponse
import jr.brian.myforecast.viewmodel.MainViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var forecastAdapter: ForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.model = viewModel
        viewModel.forecastLiveData.observe(this) {
            binding.model = viewModel
            setAdapter(it)
            Log.d("Response", "Forecast : ${it.forecast}")
        }
        viewModel.error.observe(this) {
            Log.d("TAG", it)
            Toast.makeText(baseContext, it, Toast.LENGTH_SHORT).show()
        }
        initSwipe()
    }

    private fun initSwipe() {
        binding.apply {
            swipeRefresh.setOnRefreshListener {
                if (swipeRefresh.isRefreshing) {
                    viewModel.getForecast()
                    swipeRefresh.isRefreshing = false
                }
            }
        }
    }

    private fun setAdapter(fcr: ForecastResponse) {
        forecastAdapter = ForecastAdapter(fcr.forecast.forecastday)
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.VERTICAL, false
            )
            recyclerView.adapter = forecastAdapter
        }
    }
}