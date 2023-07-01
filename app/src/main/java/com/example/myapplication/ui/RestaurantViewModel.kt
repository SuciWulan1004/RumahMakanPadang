package com.example.myapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Restaurant
import com.example.myapplication.repository.Restaurantripository
import com.example.myapplication.repository.TireRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class RestaurantViewModel (private val repository: Restaurantripository): ViewModel() {
    val allRestaurants: LiveData<List<Restaurant>> = repository.allRestaurant.asLiveData()

    fun insert(restaurant: Restaurant) = viewModelScope.launch {
        repository.insertRestaurant(restaurant)
    }

    fun delete(restaurant: Restaurant) = viewModelScope.launch {
        repository.deleteRestaurant(restaurant)
    }

    fun update(restaurant: Restaurant) = viewModelScope.launch {
        repository.updateRestaurant(restaurant)
    }
}

class RestaurantViewModelFactory(private val repository: Restaurantripository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((RestaurantViewModel::class.java))){
            return RestaurantViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}
