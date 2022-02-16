package com.example.iesbcoinapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.iesbcoinapp.CoreApplication
import com.example.iesbcoinapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoreApplication.appInBackGround = true

        setupActionBarWithNavController(navController)

        intent?.let {
            val title = it.getStringExtra("title")
            val body = it.getStringExtra("body")
            println("Ola Mundo")
            println(title)
            println(body)
        }
    }

    override fun onStop() {
        super.onStop()
        CoreApplication.appInBackGround = true
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}