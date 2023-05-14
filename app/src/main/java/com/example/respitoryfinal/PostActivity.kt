package com.example.respitoryfinal


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.respitoryfinal.databinding.ActivityPostBinding

class PostActivity: AppCompatActivity (){
    private lateinit var binding: ActivityPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}