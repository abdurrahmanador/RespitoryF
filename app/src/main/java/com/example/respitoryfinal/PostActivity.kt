package com.example.respitoryfinal

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.example.respitoryfinal.databinding.ActivityPostBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var userArrayList: ArrayList<User>
    private lateinit var adapter: Adapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var currentQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Post"
        val blueColor = ContextCompat.getColor(this, R.color.blue)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(blueColor))

        recyclerView = binding.userList
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList = ArrayList()
        adapter = Adapter(userArrayList)

        recyclerView.adapter = adapter

        searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                currentQuery = newText
                searchByCity(currentQuery)
                return true
            }
        })

        eventChangeListener()
    }

    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Donor")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if (error != null) {
                        Log.e("Firestore Error", error.message.toString())
                        return
                    }
                    userArrayList.clear()
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            userArrayList.add(dc.document.toObject(User::class.java))
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            })
    }

    private fun searchByCity(city: String) {
        db.collection("Donor")
            .orderBy("address")
            .startAt(city)
            .endAt(city + "\uf8ff")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if (error != null) {
                        Log.e("Firestore Error", error.message.toString())
                        return
                    }
                    userArrayList.clear()
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            userArrayList.add(dc.document.toObject(User::class.java))
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            })
    }

//    fun onCallDirectClicked(view: View) {
//        val intent = Intent(this, DetailsActivity::class.java)
//        startActivity(intent)
//    }
}


//
//
//package com.example.respitoryfinal
//
//
//
//import android.content.Intent
//import android.graphics.drawable.ColorDrawable
//import android.os.Bundle
//import android.util.Log
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import androidx.appcompat.widget.SearchView
//import com.example.respitoryfinal.databinding.ActivityPostBinding
//import com.google.firebase.firestore.DocumentChange
//import com.google.firebase.firestore.EventListener
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.FirebaseFirestoreException
//import com.google.firebase.firestore.Query
//import com.google.firebase.firestore.QuerySnapshot
//import com.google.firebase.firestore.core.View
//
//class PostActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityPostBinding
//    private lateinit var db: FirebaseFirestore
//    private lateinit var userArrayList: ArrayList<User>
//    private lateinit var adapter: Adapter
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var searchView: SearchView
//    private var currentQuery: String = ""
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityPostBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = "Post"
//        val blueColor = ContextCompat.getColor(this, R.color.blue)
//        supportActionBar?.setBackgroundDrawable(ColorDrawable(blueColor))
//
//        recyclerView = findViewById(R.id.userList)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.setHasFixedSize(true)
//
//        userArrayList = ArrayList()
//        adapter = Adapter(userArrayList)
//
//        recyclerView.adapter = adapter
//
//        searchView = findViewById(R.id.searchView)
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                currentQuery = newText
//                searchByCity(currentQuery)
//                return true
//            }
//        })
//
//        EventChangeListener()
//    }
//
//    private fun EventChangeListener() {
//        db = FirebaseFirestore.getInstance()
//        db.collection("Donor")
//            .addSnapshotListener(object : EventListener<QuerySnapshot> {
//                override fun onEvent(
//                    value: QuerySnapshot?,
//                    error: FirebaseFirestoreException?
//                ) {
//                    if (error != null) {
//                        Log.e("Firestore Error", error.message.toString())
//                        return
//                    }
//                    userArrayList.clear()
//                    for (dc: DocumentChange in value?.documentChanges!!) {
//                        if (dc.type == DocumentChange.Type.ADDED) {
//                            userArrayList.add(dc.document.toObject(User::class.java))
//                        }
//                    }
//                    adapter.notifyDataSetChanged()
//                }
//            })
//    }
//
//    private fun searchByCity(city: String) {
//        db.collection("Donor")
//            .orderBy("address")
//            .startAt(city)
//            .endAt(city + "\uf8ff")
//            .addSnapshotListener(object : EventListener<QuerySnapshot> {
//                override fun onEvent(
//                    value: QuerySnapshot?,
//                    error: FirebaseFirestoreException?
//                ) {
//                    if (error != null) {
//                        Log.e("Firestore Error", error.message.toString())
//                        return
//                    }
//                    userArrayList.clear()
//                    for (dc: DocumentChange in value?.documentChanges!!) {
//                        if (dc.type == DocumentChange.Type.ADDED) {
//                            userArrayList.add(dc.document.toObject(User::class.java))
//                        }
//                    }
//                    adapter.notifyDataSetChanged()
//                }
//            })
//    }
//    fun onCallDirectClicked(view: View) {
//        val intent = Intent(this, DetailsActivity::class.java)
//        startActivity(intent)
//    }
//}
