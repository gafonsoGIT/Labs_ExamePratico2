package com.example.labs_examepratico2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labs_examepratico2.adapter.PersonListAdapter
import com.example.labs_examepratico2.model.Person
import com.example.labs_examepratico2.viewModel.PersonViewModel
import com.example.labs_examepratico2.viewModel.PersonViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

  private val newPersonActivityRequestCode = 1

  private val personViewModel: PersonViewModel by viewModels {
    PersonViewModelFactory((application as PersonApplication).repository)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
    val adapter = PersonListAdapter()
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(this)

    personViewModel.allPeople.observe(this, Observer {
        people ->

      people?.let { adapter.submitList(it) }
    })

    val fab = findViewById<FloatingActionButton>(R.id.fab)
    fab.setOnClickListener {
      val intent = Intent(this@MainActivity, NewPersonActivity::class.java)
      startActivityForResult(intent, newPersonActivityRequestCode)
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
    super.onActivityResult(requestCode, resultCode, intentData)

    if (requestCode == newPersonActivityRequestCode && resultCode == Activity.RESULT_OK) {
      intentData?.getStringExtra(NewPersonActivity.EXTRA_REPLY)?.let {
        val person = Person(it)
        personViewModel.insert(person)
      }
    }
    else {
      Toast.makeText(
        applicationContext,
        R.string.empty_not_saved,
        Toast.LENGTH_LONG
      ).show()
    }
  }
}
