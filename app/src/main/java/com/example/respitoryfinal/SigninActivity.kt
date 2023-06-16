package com.example.respitoryfinal

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.respitoryfinal.HomeActivity
import com.example.respitoryfinal.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class SigninActivity : AppCompatActivity() {
// Force reCAPTCHA flow
    private lateinit var sendOTPBtn : Button
    private lateinit var phoneNumberET : EditText
    private lateinit var auth : FirebaseAuth
    private lateinit var number : String

    private lateinit var mProgressBar : ProgressBar
    private lateinit var verifyBtn: Button
    private lateinit var resendTV: TextView
    private lateinit var otpInput: EditText

    private lateinit var OTP: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var phoneNumber: String
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        //      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        supportActionBar?.hide()


        OTP = intent.getStringExtra("OTP").toString()
        phoneNumber = intent.getStringExtra("phoneNumber").toString()
        val signinLayout = findViewById<View>(R.id.signinLayout) as ConstraintLayout
        FirebaseAuth.getInstance().firebaseAuthSettings.forceRecaptchaFlowForTesting(true)

        if (haveNetwork()) {
            val snackbar =
                Snackbar.make(signinLayout, "Internet Connection Enabled!", Snackbar.LENGTH_SHORT)
                    .show()

        }
        else {
            val builder = AlertDialog.Builder(this@SigninActivity)
            builder.setTitle("Respitory ")
            builder.setMessage("Internet Connection Not Found,\nPlease enable mobile data or Wifi ")
            builder.setNegativeButton("Exit") { dialogInterface, i -> finish() }
            builder.setPositiveButton("Try Again") { dialogInterface, i -> restartApp() }
            builder.show()
        }

        init()

        sendOTPBtn.setOnClickListener {
            number = phoneNumberET.text.trim().toString()
            if (number.isNotEmpty()){
                if (number.length == 10||number.length == 11||number.length == 12||number.length == 13){
                    number = "+880$number"
                    mProgressBar.visibility = View.VISIBLE
                    val options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNumber) // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this) // Activity (for callback binding)
                        .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)
//                    val options = PhoneAuthOptions.newBuilder(auth)
//                        .setPhoneNumber(number)       // Phone number to verify
//                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                        .setActivity(this)                 // Activity (for callback binding)
//                        .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
//                        .build()
//                    PhoneAuthProvider.verifyPhoneNumber(options)

                }else{
                    Toast.makeText(this , "Please Enter a Valid Number" , Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this , "Please Enter Number" , Toast.LENGTH_SHORT).show()

            }
        }

        resendTV.setOnClickListener {
            resendVerificationCode()
            resendOTPTvVisibility()
        }

        verifyBtn.setOnClickListener {
            //collect otp from all the edit texts
            val typedOTP = otpInput.text.toString()
            if (typedOTP.isNotEmpty()) {
                if (typedOTP.length >=0 || typedOTP.length<=15) {
                    val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                        OTP, typedOTP
                    )
                    Toast.makeText(this, "Please Wait few moments,\n or check your code Again", Toast.LENGTH_SHORT).show()


                    signInWithPhoneAuthCredential(credential)
                } else {
                    Toast.makeText(this, "Please Enter Correct OTP", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }


    }//end ONCREATE

    private fun haveNetwork(): Boolean {
        var haveWifi = false
        var haveMobile = false
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfos = connectivityManager.allNetworkInfo
        for (info in networkInfos) {
            if (info.typeName.equals("WIFI", ignoreCase = true)) if (info.isConnected) haveWifi =
                true
            if (info.typeName.equals(
                    "MOBILE",
                    ignoreCase = true
                )
            ) if (info.isConnected) haveMobile = true
        }
        return haveMobile || haveWifi
    }

    private fun restartApp() {
        startActivity(Intent(applicationContext, SigninActivity::class.java))
        finish()
    }

    private fun resendOTPTvVisibility() {
        otpInput.setText("")
        resendTV.visibility = View.INVISIBLE
        resendTV.isEnabled = false

        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            resendTV.visibility = View.VISIBLE
            resendTV.isEnabled = true
        }, 60000)
    }

    private fun resendVerificationCode() {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)
            .setForceResendingToken(resendToken)// OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishAffinity()
        } else {
            Toast.makeText(this, "Tap Twice to exit.", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    private fun init(){
        mProgressBar = findViewById(R.id.verficationProgressBar)
        mProgressBar.visibility = View.INVISIBLE
        sendOTPBtn = findViewById(R.id.verificationButton)
        phoneNumberET = findViewById(R.id.contactNumberET)
        auth = FirebaseAuth.getInstance()
        verifyBtn = findViewById(R.id.OTPButton)
        otpInput = findViewById(R.id.OTPET)
        resendTV = findViewById(R.id.resendTextView)

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this , "Authenticate Successfully" , Toast.LENGTH_SHORT).show()
                    sendToHome()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.d("TAG", "signInWithPhoneAuthCredential: ${task.exception.toString()}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
                mProgressBar.visibility = View.INVISIBLE
            }
    }

    private fun sendToHome(){
        startActivity(Intent(this , HomeActivity::class.java))
    }


    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            }
            mProgressBar.visibility = View.VISIBLE
            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            // Save verification ID and resending token so we can use them later
            mProgressBar.visibility = View.INVISIBLE
            OTP = verificationId
            resendToken = token
        }
        private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this@SigninActivity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information

                        Toast.makeText(this@SigninActivity, "Authenticate Successfully", Toast.LENGTH_SHORT).show()
                        sendToHome()
                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.d("TAG", "signInWithPhoneAuthCredential: ${task.exception.toString()}")
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                        }
                        // Update UI
                    }
                }
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            startActivity(Intent(this , HomeActivity::class.java))
        }
    }
}
