package com.pinankh.myweather.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.pinankh.myweather.R
import com.pinankh.myweather.databinding.ActivityWeatherTrackerHomeBinding
import com.pinankh.myweather.models.WeatherViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll

class WeatherTrackerHome : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherTrackerHomeBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWeatherTrackerHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.searchButton.setOnClickListener {
            val city = binding.cityEditText.text.toString()
            viewModel.getWeather(city)
        }

        lifecycleScope.launch {
            viewModel.weather.collectLatest { weather ->

                binding.cityName.text = weather.name ?: ""
                binding.temperature.text = "%.0f°C".format(weather.main?.temp?.minus(273.15))
                binding.feelsLike.text = "Feels like: %.0f°C".format(weather.main?.feels_like?.minus(273.15))
                binding.humidity.text = "Humidity: ${weather?.main?.humidity}%"

                Glide.with(this@WeatherTrackerHome)
                    .load("http://openweathermap.org/img/wn/${weather.weather?.get(0)?.icon}@2x.png")
                    .into(binding.weatherIcon)

                binding.weatherLayout.isVisible = true
                binding.noCitySelected.isVisible = false
            }
        }
    }
    }
}