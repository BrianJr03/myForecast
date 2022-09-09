package jr.brian.myforecast.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import jr.brian.myforecast.R
import jr.brian.myforecast.databinding.ForecastItemBinding
import jr.brian.myforecast.model.remote.response.Forecastday

class ForecastAdapter(private val forecasts: List<Forecastday>) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    override fun getItemCount() = forecasts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ForecastItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.forecast_item, parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.apply {
            val forecast = forecasts[position]
            bind(forecast)
        }
    }

    inner class ForecastViewHolder(private val b: ForecastItemBinding) :
        RecyclerView.ViewHolder(b.root) {
        fun bind(forecast: Forecastday) {
            b.model = forecast
        }
    }
}