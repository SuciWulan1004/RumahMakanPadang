package com.example.myapplication.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.dao.RestaurantDao
import com.example.myapplication.model.Restaurant


@Database(entities = [Restaurant::class], version = 2, exportSchema = false)
abstract class RestaurantDatabase: RoomDatabase() {
    abstract fun RestaurantDao(): RestaurantDao

    companion object{
        private var INSTANCE : RestaurantDatabase? = null

        private val migration1To2: Migration = object: Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Restaurant_table ADD COLUMN latitude Double DEFAULT 0.0")
                database.execSQL("ALTER TABLE Restaurant_table ADD COLUMN longitude Double DEFAULT 0.0")
            }

        }

        fun getDatabase(context: Context): RestaurantDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RestaurantDatabase::class.java,
                    "Restaurant_Database_1"
                )
                    .addMigrations(migration1To2)
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

}