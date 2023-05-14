package com.example.respitoryfinal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.respitoryfinal.Donateus
import com.example.respitoryfinal.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.needOxygen.setOnClickListener {
            val intent=Intent(this,PostActivity::class.java)
            startActivity(intent)
        }

        binding?.donateUs?.setOnClickListener {
            val intent=Intent(this, Donateus::class.java)
            startActivity(intent)
        }

    }
}
