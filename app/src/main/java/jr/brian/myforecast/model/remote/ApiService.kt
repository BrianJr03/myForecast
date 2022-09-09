package jr.brian.myforecast.model.remote

import io.reactivex.rxjava3.core.Single
import jr.brian.myforecast.model.remote.response.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") q: String,
        @Query("days") days: String
    ): Response<ForecastResponse>

    @GET("forecast.json")
    fun getForecastRx(
        @Query("q") q: String,
        @Query("days") days: String
    ): Single<ForecastResponse>
}