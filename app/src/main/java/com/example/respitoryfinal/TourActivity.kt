package com.example.respitoryfinal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.respitoryfinal.HomeActivity
import com.example.respitoryfinal.R
import com.example.respitoryfinal.databinding.ActivityMainBinding
import java.util.*


class TourActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.logoImage.alpha = 0f

        binding!!.logoImage.animate().setDuration(1600).alpha(1f).withEndAction {
            startActivity(Intent(this@TourActivity, HomeActivity::class.java))
            overridePendingTransition(R.anim.top_animation, R.anim.bottom_animation)
            finish()
        }

        binding!!.txt.animate().setDuration(1600).alpha(1f).withEndAction {
            startActivity(Intent(this@TourActivity, HomeActivity::class.java))
            overridePendingTransition(R.anim.top_animation, R.anim.bottom_animation)
            finish()
        }

        Objects.requireNonNull(supportActionBar)?.hide()
    }
}