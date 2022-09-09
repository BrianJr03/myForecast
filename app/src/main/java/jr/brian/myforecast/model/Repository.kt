package jr.brian.myforecast.model

import jr.brian.myforecast.model.remote.ApiService
import jr.brian.myforecast.util.Constants.API_KEY
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {
    suspend fun getForecast(cityName: String) = apiService.getForecast(q = cityName, days = "10")

     fun getColorsRx(cityName: String) = apiService.getForecastRx(q= cityName, days = "10")
}