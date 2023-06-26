package com.example.myapplication.repository

import com.example.myapplication.dao.RestaurantDao
import com.example.myapplication.model.Restaurant
import kotlinx.coroutines.flow.Flow

class Restaurantripository {
}
class TireRepository(private val RestaurantDao: RestaurantDao) {
    val allTires: Flow<List<Restaurant>> = RestaurantDao.getAllRestaurant()
    suspend fun insertTire(tire: Restaurant){
        RestaurantDao.insertRestaurant(tire)
    }

    suspend fun deleteRestaurant(Restaurant: Restaurant){
        RestaurantDao.deletRestaurant(Restaurant)
    }

    suspend fun updateTire(tire: Restaurant){
        RestaurantDao.updateRestaurant(tire)
    }
}