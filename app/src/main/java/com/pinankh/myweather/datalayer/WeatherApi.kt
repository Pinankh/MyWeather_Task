package com.pinankh.myweather.datalayer

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String = "6a64edc6b0094b4b971104057242512"
    ): WeatherResponse
}