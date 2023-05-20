package com.example.respitoryfinal

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.respitoryfinal.databinding.ActivityPostBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var userArrayList: ArrayList<User>
    private lateinit var adapter: Adapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView=findViewById(R.id.userList)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList= arrayListOf()
        adapter=Adapter(userArrayList)

        recyclerView.adapter=adapter

        EventChangeListener()
    }

    private fun EventChangeListener(){
        db= FirebaseFirestore.getInstance()
        db.collection("Donor")
            .addSnapshotListener(object: EventListener<QuerySnapshot>{
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        if(error!=null){
                            Log.e("Firestore Error",error.message.toString())
                            return
                        }
                        for (dc: DocumentChange in value?.documentChanges!!){
                            if(dc.type==DocumentChange.Type.ADDED){
                                userArrayList.add(dc.document.toObject(User::class.java))
                            }
                        }
                        adapter.notifyDataSetChanged()
                    }
                })
    }
}
/*


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val materialCardView = binding.materialCardView
        val searchView = binding.searchView

        userList = binding.userList
        userList.layoutManager = LinearLayoutManager(this)
        userListAdapter = UserListAdapter(emptyList())
        userList.adapter = userListAdapter

        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchUsers(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchUsers(newText)
                return false
            }
        })
    }

    private fun searchUsers(query: String) {
        db.collection("Donor")
            .whereEqualTo("city", query)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val users = mutableListOf<User>()

                for (documentSnapshot in querySnapshot) {
                    val data = documentSnapshot.data
                    val bottleType = data["bottleType"] as String
                    val bottleNeed = data["bottleNeed"] as Long
                    val contact = data["contact"] as String
                    val address = data["address"] as String
                    val exactAddress = data["exactAddress"] as String

                   // val user = User(bottleType, bottleNeed, contact, address, exactAddress)
                   // users.add(user)
                }

                userListAdapter.setUsers(users)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed! Please Try Again!", Toast.LENGTH_SHORT).show()
            }
    }
}
*/