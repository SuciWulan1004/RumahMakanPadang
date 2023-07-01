package com.example.myapplication.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.application.RestaurantApp
import com.example.myapplication.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private lateinit var applicationContext: Context
    private val RestaurantViewModel: RestaurantViewModel by viewModels {
        RestaurantViewModelFactory((applicationContext as RestaurantApp).repository)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RestaurantListAdapter { restaurant->
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(restaurant)
            findNavController().navigate(action)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        RestaurantViewModel.allRestaurants.observe(viewLifecycleOwner){ restaurants ->
            restaurants.let {
                if (restaurants.isEmpty()) {
                    binding.empityTextView.visibility = View.VISIBLE
                    binding.ilustrationImageView.visibility = View.VISIBLE
                }else{
                    binding.empityTextView.visibility = View.GONE
                    binding.ilustrationImageView.visibility = View.GONE
                }
                adapter.submitList(restaurants)
            }
        }

        binding.addFAB.setOnClickListener {

            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
            findNavController().navigate(action)
     }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}