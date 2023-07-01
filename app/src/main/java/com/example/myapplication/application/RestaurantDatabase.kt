package com.example.myapplication.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.dao.RestaurantDao
import com.example.myapplication.model.Restaurant


@Database(entities = [Restaurant::class], version = 1, exportSchema = false)
abstract class RestaurantDatabase: RoomDatabase() {
    abstract fun RestaurantDao(): RestaurantDao

    companion object{
        private var INSTANCE : RestaurantDatabase? = null

        fun getDatabase(context: Context): RestaurantDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RestaurantDatabase::class.java,
                    "Restaurant_Database_1"
                )
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

}