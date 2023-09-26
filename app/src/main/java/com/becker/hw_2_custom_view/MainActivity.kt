package com.becker.hw_2_custom_view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.becker.hw_2_custom_view.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}