package com.kulloveth.moviesapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kulloveth.moviesapp.R
import com.kulloveth.moviesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setup bottomNavigation with Navigation Component
        val bottomNavView: BottomNavigationView = binding.navView;
        val navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )
        NavigationUI.setupWithNavController(bottomNavView, navController)


    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}
