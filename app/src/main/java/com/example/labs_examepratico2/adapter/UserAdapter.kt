package com.example.labs_examepratico2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.labs_examepratico2.R
import com.example.labs_examepratico2.api.User

class UserAdapter(val users: List<User>): RecyclerView.Adapter<UsersViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerline,
      parent, false)
    return UsersViewHolder(view)
  }

  override fun getItemCount(): Int {
    return users.size
  }

  override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
    return holder.bind(users[position])
  }
}

class UsersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
  private val name: TextView = itemView.findViewById(R.id.name)
  private val email: TextView = itemView.findViewById(R.id.email)
  private val address: TextView = itemView.findViewById(R.id.address)

  fun bind(user: User) {
    name.setText(user.name)
    email.setText(user.email)
    address.setText(user.address.city + " - " + user.address.zipcode)
  }
}
