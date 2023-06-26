package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.model.Restaurant
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {
    @Query("SELECT * FROM 'Restaurant_table' ORDER BY name ASC")
    fun getAllRestaurant(): Flow<List<Restaurant>>

    @Insert
    suspend fun insertRestaurant(Restaurant: Restaurant)

    @Delete
    suspend fun deletRestaurant(Restaurant: Restaurant)

    @Update
    fun updateRestaurant(Restaurant: Restaurant)
}