package com.example.myapplication.application

import android.app.Application
import com.example.myapplication.repository.Restaurantripository

class RestaurantApp: Application () {
    val database by lazy { RestaurantDatabase.getDatabase(this) }
    val repository by lazy { Restaurantripository(database.RestaurantDao()) }
}