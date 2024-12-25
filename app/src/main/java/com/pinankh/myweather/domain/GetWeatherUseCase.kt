package com.pinankh.myweather.domain

import com.pinankh.myweather.datalayer.WeatherResponse

interface GetWeatherUseCase {
    suspend operator fun invoke(city: String): Result<WeatherResponse>
}