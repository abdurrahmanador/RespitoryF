package com.example.respitoryfinal

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.respitoryfinal.databinding.ActivityDonationBinding
import com.google.firebase.firestore.FirebaseFirestore

class Donateus : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding: ActivityDonationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val district = resources.getStringArray(R.array.district_name)
        district.sort()
        val dAdapter = ArrayAdapter(this, R.layout.drop_down_item, district)
        binding.updateDistrictET.setAdapter(dAdapter)

        val donationType = resources.getStringArray(R.array.DonationType)
        donationType.sort()
        val donationAdapter = ArrayAdapter(this, R.layout.drop_down_item, donationType)
        binding.donateType.setAdapter(donationAdapter)
        firestore = FirebaseFirestore.getInstance()
        binding.updateButtonID.setOnClickListener {
            if(binding.updateDistrictET==null && binding.updateContactNumberET==null && binding.updateEmailProfileEt==null && binding.updateNameProfileEt==null
                &&binding.donateType==null){
                Toast.makeText(
                    this,
                    "Please Provide all Information Correctly!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            saveDonationData()
        }
    }

    private fun saveDonationData() {
        val name: String = binding.updateNameProfileEt.text.toString().trim()
        val contactNumber: String = binding.updateContactNumberET.text.toString().trim()
        val email: String = binding.updateEmailProfileEt.text.toString().trim()
        val district: String = binding.updateDistrictET.text.toString().trim()
        val donationType: String = binding.donateType.text.toString().trim()

        // Create a new document with a generated ID
        val donationData = hashMapOf(
            "name" to name,
            "contactNumber" to contactNumber,
            "email" to email,
            "district" to district,
            "donationType" to donationType
        )

        firestore.collection("donations")
            .add(donationData)
            .addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Donation saved successfully,\nWe Will contact with you soon..",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error saving donation: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}
