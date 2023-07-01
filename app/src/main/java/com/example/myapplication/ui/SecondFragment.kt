package com.example.myapplication.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.application.RestaurantApp
import com.example.myapplication.databinding.FragmentSecondBinding
import com.example.myapplication.model.Restaurant

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val RestaurantViewModel: RestaurantViewModel by viewModels {
        RestaurantViewModelFactory((applicationContext as RestaurantApp).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var restaurant: Restaurant? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restaurant = args.restaurant
        if(restaurant != null) {
            binding.deleteButton.visibility = View.VISIBLE
            binding.buttonSecond.text = "ubah"
            binding.nameEditText.setText(restaurant?.name)
            binding.addressEditText.setText(restaurant?.address)
        }
        val name = binding.nameEditText.text
        val address = binding.addressEditText.text
        binding.buttonSecond.setOnClickListener {
            if (name.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (address.isEmpty()){
                    Toast.makeText(context, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()

            } else {
                if ( restaurant == null){
                    val restaurant = Restaurant (0, name.toString(), address.toString())
                    RestaurantViewModel.insert(restaurant)

                } else{
                    val restaurant = Restaurant (restaurant?.id!!, name.toString(), address.toString())
                    RestaurantViewModel.update(restaurant)
                }
                findNavController().popBackStack()
            }

        }
        binding.deleteButton.setOnClickListener {
            restaurant?.let { RestaurantViewModel.delete(it) }
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}