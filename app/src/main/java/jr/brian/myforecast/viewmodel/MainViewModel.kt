package jr.brian.myforecast.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jr.brian.myforecast.model.Repository
import jr.brian.myforecast.model.remote.ForecastResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val city = MutableLiveData<String>()
    val forecastLiveData = MutableLiveData<ForecastResponse>()
    val error = MutableLiveData<String>()

    fun getForecast() {
        val c = city.value
        if (c == null || c.isEmpty()) {
            error.postValue("Please enter City name")
            return
        }
        viewModelScope.launch(IO) {
            try {
                val response = repository.getForecast(c)
                if (!response.isSuccessful) {
                    error.postValue("Failed to load data from server. Retry.")
                    return@launch
                }

                val forecastResponse = response.body()

                if (forecastResponse == null) {
                    error.postValue("Empty response from the server")
                    return@launch
                }

                forecastLiveData.postValue(forecastResponse)

            } catch (e: Exception) {
                e.printStackTrace()
                error.postValue("Error is : $e")
            }
        }
    }
}