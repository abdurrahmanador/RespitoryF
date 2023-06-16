package com.example.respitoryfinal




import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.respitoryfinal.Donateus
import com.example.respitoryfinal.databinding.ActivityHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var backPressedTime: Long = 0

    lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("Home")
        val blueColor = ContextCompat.getColor(this, R.color.blue)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(blueColor))

        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawarLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home ->{
                    startActivity(Intent(this,HomeActivity::class.java))
                }
                R.id.nav_login -> Toast.makeText(
                    applicationContext,
                    "You Are already Logged In",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_logout -> {
                    logOut()

                }
                R.id.nav_about ->
                    startActivity(Intent(this, About::class.java))

                R.id.nav_share -> {
                    val appPackageName = BuildConfig.APPLICATION_ID
                    val appName = "Respiration"
                    val shareBodyText =
                        "Check out this amazing app: $appName\n\nDownload link: https://play.google.com/store/apps/details?id=$appPackageName"

                    val sendIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, "Check out this app: $appName")
                        putExtra(Intent.EXTRA_TEXT, shareBodyText)
                    }

                    val shareIntent = Intent.createChooser(sendIntent, "Share via")
                    startActivity(shareIntent)

                }
            }
            true
        }

        binding.needOxygen.setOnClickListener {
            val intent=Intent(this,PostActivity::class.java)
            startActivity(intent)
        }

        binding.donateUs.setOnClickListener {
            val intent=Intent(this, Donateus::class.java)
            startActivity(intent)
        }
        binding.logOut.setOnClickListener {
            logOut()
        }

        binding.about.setOnClickListener {
            val intent=Intent(this,About::class.java)
            startActivity(intent)
        }
    }
    fun logOut(){
        val auth=FirebaseAuth.getInstance()
        val googleSignInClient=GoogleSignIn.getClient(applicationContext,GoogleSignInOptions.DEFAULT_SIGN_IN)
        auth.signOut()
        googleSignInClient.signOut().addOnCompleteListener {task->
            if(task.isSuccessful){
                startActivity(Intent(this,EmailVerification::class.java))
            }else{
                Toast.makeText(this,"Sorry! Log Out Fail ,try again later.",Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishAffinity()
        } else {
            Toast.makeText(this, "Tap again to exit.", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
