package jr.brian.myforecast.model.remote.response

data class ForecastResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)