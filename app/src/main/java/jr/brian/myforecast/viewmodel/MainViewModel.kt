package jr.brian.myforecast.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import jr.brian.myforecast.model.Repository
import jr.brian.myforecast.model.remote.response.ForecastResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val city = MutableLiveData<String>()
    val forecastLiveData = MutableLiveData<ForecastResponse>()
    val error = MutableLiveData<String>()

    private var compositeDisposable = CompositeDisposable()

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

    fun getForecastRx() {
        val c = city.value
        if (c == null || c.isEmpty()) {
            error.postValue("Please enter City name")
            return
        }
        compositeDisposable.addAll(
            repository.getColorsRx(c)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { forecastLiveData.postValue(it) },
                    { error.postValue("Error is : ${it.message}") }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}