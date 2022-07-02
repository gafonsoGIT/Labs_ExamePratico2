package com.example.labs_examepratico2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.labs_examepratico2.R
import com.example.labs_examepratico2.model.Person
import java.util.*

class PersonListAdapter : ListAdapter<Person, PersonListAdapter.PersonViewHolder>(PersonsComparator()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
    return PersonViewHolder.create(parent)
  }

  override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
    val current = getItem(position)
    holder.bind(current)
  }

  class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val personItemView: TextView = itemView.findViewById(R.id.textView)

    fun bind(current: Person) {
      val currentYear = Calendar.getInstance().get(Calendar.YEAR)
      val idadeAtual = currentYear - current.anoNascimento
      var text = ""
      if(current.idade < idadeAtual) {
        text = "Ainda vai fazer anos"
      } else {
        text = "Já fez anos"
      }
      personItemView.text = current.nome + " (" + current.idade.toString() + ") " + text
    }

    companion object {
      fun create(parent: ViewGroup): PersonViewHolder {
        val view: View = LayoutInflater.from(parent.context)
          .inflate(R.layout.recyclerview_item, parent, false)
        return PersonViewHolder(view)
      }
    }
  }

  class PersonsComparator : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
      return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
      return oldItem.nome == newItem.nome
    }
  }
}
