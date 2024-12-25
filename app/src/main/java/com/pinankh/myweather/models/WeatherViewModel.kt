package com.pinankh.myweather.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinankh.myweather.datalayer.WeatherResponse
import com.pinankh.myweather.datastore.CityDataStore
import com.pinankh.myweather.domain.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val cityDataStore: CityDataStore
) : ViewModel() {

    private val _weather = MutableStateFlow<Result<WeatherResponse>?>(null)
    val weather: StateFlow<Result<WeatherResponse>?> = _weather

    init {
        viewModelScope.launch {
            cityDataStore.selectedCity.collectLatest { city ->
                city?.let { getCityWeather(it) }
            }
        }
    }

    private fun getCityWeather(city: String) {
        viewModelScope.launch {
            try {
                val weatherResponse = getWeatherUseCase(city)
                _weather.value = weatherResponse
                cityDataStore.saveCity(city)
            } catch (e: Exception) {
                // Handle errors (e.g., display an error message to the user)
                _weather.value = null
            }
        }
    }
    fun getWeather(city: String) {
        viewModelScope.launch {
            _weather.value = getWeatherUseCase(city)
        }
    }
}