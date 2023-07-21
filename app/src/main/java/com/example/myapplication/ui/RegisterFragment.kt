package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation


class RegisterFragment :  Fragment() {
    private lateinit var txt_3r: TextView
    private lateinit var txt_4r: TextView
    private lateinit var txt_5r: TextView
    private lateinit var btn_1r: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        txt_3r = view.findViewById(R.id.txt_3r)
        txt_4r = view.findViewById(R.id.txt_4r)
        txt_5r = view.findViewById(R.id.txt_5r)
        btn_1r = view.findViewById(R.id.btn_1r)

        btn_1r.setOnClickListener {
            val username = txt_3r.text.toString()
            val password = txt_4r.text.toString()
            val confirmPassword = txt_5r.text.toString()

            // Perform register authentication
            val isAuthenticated = authenticateUser(username, confirmPassword, password)

            if (isAuthenticated) {
                // Register success, perform necessary actions
                Toast.makeText(activity, "register Successful", Toast.LENGTH_SHORT).show()
                val navController = Navigation.findNavController(
                    requireActivity(),
                    R.id.nav_host_fragment_content_main
                )
                navController.navigate(R.id.action_RegisterFragment_to_SignInFragment)
            } else {
                // Login failed, show error message
                Toast.makeText(activity, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun authenticateUser(username: String, confirmPassword: String, password: String): Boolean {
        // Implement your authentication logic here
        // This is just a sample implementation
        return username == "ninahidayah" && password == "1412200068" && confirmPassword == "1412200068"
    }
}
