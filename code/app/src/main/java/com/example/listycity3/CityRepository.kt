package com.example.listycity3

import androidx.compose.runtime.mutableStateListOf

class CityRepository {
    private val _cities = mutableStateListOf<City>(
        City("Edmonton", "AB"),
        City("Vancouver", "BC"),
        City("Toronto", "ON")
    )

    val cities: List<City>
        get() = _cities

    fun modifyCity(city: City, index: Int) {
        if (index >= _cities.size){
            addCity(city)
        }
        else {
            _cities[index] = city
        }
    }

    fun addCity(city: City) {
        _cities.add(city)
    }
}