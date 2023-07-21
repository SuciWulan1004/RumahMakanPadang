package com.example.myapplication.ui

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.application.RestaurantApp
import com.example.myapplication.databinding.FragmentSecondBinding
import com.example.myapplication.model.Restaurant
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment: Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val RestaurantViewModel: RestaurantViewModel by viewModels {
        RestaurantViewModelFactory((applicationContext as RestaurantApp).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var restaurant: Restaurant? = null
    private lateinit var mMap: GoogleMap
    private var currentLatLang: LatLng? = null
    private lateinit var fuseLocationClient: FusedLocationProviderClient

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
            binding.ownerEditText.setText(restaurant?.address)
        }

        //binding google map
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync (this)
        checkPermission()



        val name = binding.nameEditText.text
        val address = binding.addressEditText.text
        val owner = binding.ownerEditText.text
        binding.buttonSecond.setOnClickListener {
            if (name.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (address.isEmpty()){
                    Toast.makeText(context, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (owner.isEmpty()){
                Toast.makeText(context, "Nama Pemilik tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                if ( restaurant == null){
                    val restaurant = Restaurant (0, name.toString(), address.toString(), currentLatLang?.latitude, currentLatLang?.longitude)
                    RestaurantViewModel.insert(restaurant)

                } else{
                    val restaurant = Restaurant (restaurant?.id!!, name.toString(), address.toString(), currentLatLang?.latitude, currentLatLang?.longitude)
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

    override fun OnMapReady(googleMap: GoogleMap){
        mMap = googleMap
        //implement drag marker

        val uiSettings = mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerDragListener(this)
    }

    override fun OnMarkerDrag(p0: Marker){}

    override fun OnMarkerDragEnd(marker: Marker){
        val newPosition = marker.position
        currentLatLang = LatLng(newPosition.latitude, newPosition.longitude)
        Toast.makeText(context, currentLatLang.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun OnMarkerDragStart(p0: Marker){

    }

    private fun checkPermission() {
        fuseLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ContextCompat.checkSelfPermission(
                applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ){

        }else {
            Toast.makeText(applicationContext, "Akses lokasi ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentLocation() {

        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ){
            return
        }

        fuseLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    var latLng = LatLng(location.latitude, location.latitude)
                    currentLatLang = latLng
                    var title = "Marker"

                    if (title != null) {
                        title = restaurant?.name.toString()
                        val newCurrentLocation = LatLng(restaurant?.latitude!!, restaurant?.longitude!!)
                        latLng = newCurrentLocation
                    }

                    val markerOption = MarkerOptions()
                        .position(latLng)
                        .title(title)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_restaurant_loc))
                    mMap.addMarker(markerOption)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                }
            }
    }
}