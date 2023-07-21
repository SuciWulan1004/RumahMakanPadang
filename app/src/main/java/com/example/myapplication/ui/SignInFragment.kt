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


class SignInFragment :  Fragment(){


    private lateinit var txt_3m: TextView
    private lateinit var txt_4m: TextView
    private lateinit var btn_1m: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        txt_3m = view.findViewById(R.id.txt_3m)
        txt_4m = view.findViewById(R.id.txt_4m)
        btn_1m = view.findViewById(R.id.btn_1m)

        btn_1m.setOnClickListener {
            val username = txt_3m.text.toString()
            val password = txt_4m.text.toString()

            // Perform login authentication
            val isAuthenticated = authenticateUser(username, password)

            if (isAuthenticated) {
                // Login success, perform necessary actions
                Toast.makeText(activity, "Sign In Successful", Toast.LENGTH_SHORT).show()
                val navController = Navigation.findNavController(
                    requireActivity(),
                    R.id.nav_host_fragment_content_main
                )
                navController.navigate(R.id.action_SignInFragment_to_FirstFragment)
            } else {
                // Login failed, show error message
                Toast.makeText(activity, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
        // Menghubungkan fungsi onClickRegister(view: View) dengan TextView "txt_6"
        view.findViewById<TextView>(R.id.txt_6)?.setOnClickListener {
            onClickRegister(it)
        }
        return view
    }
    // Fungsi untuk membuka halaman Register saat TextView "txt_6" diklik
    private fun onClickRegister(view: View) {
        val navController = Navigation.findNavController(
            requireActivity(),
            R.id.nav_host_fragment_content_main
        )
        navController.navigate(R.id.action_SignInFragment_to_RegisterFragment)
    }

    private fun authenticateUser(username: String, password: String): Boolean {
        // Implement your authentication logic here
        // This is just a sample implementation
        return username == "ninahidayah" && password == "1412200068"
    }
}