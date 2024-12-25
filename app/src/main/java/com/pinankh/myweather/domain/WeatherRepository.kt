package com.pinankh.myweather.domain

import com.pinankh.myweather.datalayer.WeatherResponse

interface WeatherRepository {
    suspend fun getWeather(city: String): Result<WeatherResponse>
}