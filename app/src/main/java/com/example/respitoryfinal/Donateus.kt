package com.example.respitoryfinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.respitoryfinal.databinding.ActivityDonationBinding

class Donateus:AppCompatActivity() {

    private lateinit var binding: ActivityDonationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDonationBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}