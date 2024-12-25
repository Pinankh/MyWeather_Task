package com.pinankh.myweather.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CityDataStore(private val dataStore: DataStore<Preferences>) {

    private val selectedCityKey = stringPreferencesKey(MyPreferences.SELECTED_CITY)

    val selectedCity: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[selectedCityKey]
        }

    suspend fun saveCity(city: String) {
        dataStore.edit { preferences ->
            preferences[selectedCityKey] = city
        }
    }
}