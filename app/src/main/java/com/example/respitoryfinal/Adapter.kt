package com.example.respitoryfinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val userList: ArrayList<User>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bottleTypeTextView: TextView = itemView.findViewById(R.id.bottolTypeE)
        val bottleNeedTextView: TextView = itemView.findViewById(R.id.bottolNeed)
        val contactTextView: TextView = itemView.findViewById(R.id.contact)
        val addressTextView: TextView = itemView.findViewById(R.id.address)
        val exactAddressTextView: TextView = itemView.findViewById(R.id.exactAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.group_donor, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: User = userList[position]
        holder.bottleTypeTextView.text = user.bottleType
        holder.bottleNeedTextView.text = user.bottleNeed.toString()
        holder.contactTextView.text = user.contact.toString()
        holder.addressTextView.text = user.address
        holder.exactAddressTextView.text =user.exactAddress
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
//
//package com.example.respitoryfinal
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//class Adapter(private val list: ArrayList<User>) : RecyclerView.Adapter<Adapter.MyViewHolder>() {
//
//    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val bottleTypeTextView: TextView = itemView.findViewById(R.id.bottolTypeE)
//        val bottleNeedTextView: TextView = itemView.findViewById(R.id.bottolNeed)
//        val contactTextView: TextView = itemView.findViewById(R.id.contact)
//        val addressTextView: TextView = itemView.findViewById(R.id.address)
//        val exactAddressTextView: TextView = itemView.findViewById(R.id.exactAddress)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.group_donor, parent, false)
//        return MyViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val currentUser = list[position]
//        holder.bottleTypeTextView.text = currentUser.bottleType
//        holder.bottleNeedTextView.text = currentUser.bottleNeed.toString()
//        holder.contactTextView.text = currentUser.contact.toString()
//        holder.addressTextView.text = currentUser.address
//        holder.exactAddressTextView.text = currentUser.exactAddress
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//}
//
//
//
//
//
//
//
//
////-----------------
////package com.example.respitoryfinal
////
////
////import android.view.LayoutInflater
////import android.view.View
////import android.view.ViewGroup
////import android.widget.TextView
////import androidx.recyclerview.widget.RecyclerView
////
////class Adapter(private val list:ArrayList<User>): RecyclerView.Adapter<Adapter.MyViewHolder>()  {
////
////    public class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
////        val bottleTypeTextView: TextView = itemView.findViewById(R.id.bottolTypeE)
////        val bottleNeedTextView: TextView = itemView.findViewById(R.id.bottolNeed)
////        val contactTextView: TextView = itemView.findViewById(R.id.contact)
////        val addressTextView: TextView = itemView.findViewById(R.id.address)
////        val exactAddressTextView: TextView = itemView.findViewById(R.id.exactAddress)
////
////    }
////
////    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.MyViewHolder {
////        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.group_donor, parent, false)
////        return MyViewHolder(itemView)
////    }
////
////    override fun onBindViewHolder(holder: Adapter.MyViewHolder, position: Int) {
////        val currentUser = list[position]
////        holder.bottleTypeTextView.text = currentUser.bottleType
////        holder.bottleNeedTextView.text = currentUser.bottleNeed.toString()
////        holder.contactTextView.text = currentUser.contact.toString()
////        holder.addressTextView.text = currentUser.address
////        holder.exactAddressTextView.text = currentUser.exactAddress
////    }
////
////    override fun getItemCount(): Int {
////        return list.size
////    }
////}