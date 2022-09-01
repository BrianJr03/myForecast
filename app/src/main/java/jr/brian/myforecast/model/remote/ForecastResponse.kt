package jr.brian.myforecast.model.remote

data class ForecastResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)