package com.example.respitoryfinal

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.respitoryfinal.databinding.AboutBinding
import com.example.respitoryfinal.databinding.ActivityHomeBinding

class About :AppCompatActivity(){
    private lateinit var binding: AboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("About")
        val blueColor = ContextCompat.getColor(this, R.color.blue)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(blueColor))
        super.onCreate(savedInstanceState)
        binding=AboutBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}