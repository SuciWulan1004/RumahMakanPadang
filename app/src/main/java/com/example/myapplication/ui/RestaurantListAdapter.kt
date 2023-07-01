package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapplication.R
import com.example.myapplication.model.Restaurant

class RestaurantListAdapter(
    private val onItemClickListener: (Restaurant) -> Unit
): ListAdapter<Restaurant, RestaurantListAdapter. RestaurantViewHolder>(WORD_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        return RestaurantViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = getItem(position)
        holder.bind(restaurant)
        holder.itemView.setOnClickListener {
            onItemClickListener(restaurant)
        }
    }
    class RestaurantViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)

        fun bind(restaurant: Restaurant?) {
            nameTextView.text = restaurant?.name
            addressTextView.text = restaurant?.address
        }

        companion object {
            fun create(parent: ViewGroup): RestaurantListAdapter.RestaurantViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_restaurant, parent, false)
                return RestaurantViewHolder(view)
            }
        }
    }
    companion object{
        private val WORD_COMPARATOR = object : DiffUtil.ItemCallback<Restaurant>() {
            override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}

