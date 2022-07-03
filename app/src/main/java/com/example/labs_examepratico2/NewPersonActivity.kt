package com.example.labs_examepratico2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.example.labs_examepratico2.model.Person

class NewPersonActivity : AppCompatActivity() {

  private lateinit var editPersonName: EditText
  private lateinit var editPersonEmail: EditText
  private lateinit var editPersonAge: EditText
  private lateinit var editPersonYear: EditText


  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_person)

        editPersonName = findViewById(R.id.etNome)
        editPersonEmail = findViewById(R.id.etEmail)
        editPersonAge = findViewById(R.id.etIdade)
        editPersonYear = findViewById(R.id.etAno)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
          val replyIntent = Intent()
          if(TextUtils.isEmpty(editPersonName.text) || TextUtils.isEmpty(editPersonEmail.text) || TextUtils.isEmpty(editPersonAge.text) || TextUtils.isEmpty(editPersonYear.text)) {
            setResult(Activity.RESULT_CANCELED, replyIntent)
          }
          else {
            val name = editPersonName.text.toString()
            val email = editPersonEmail.text.toString()
            val age = editPersonAge.text.toString().toInt()
            val year = editPersonYear.text.toString().toInt()

            val people = Person(name, email, age, year)

            replyIntent.putExtra(EXTRA_REPLY, people)
            setResult(Activity.RESULT_OK, replyIntent)
          }
          finish()
        }
    }

  companion object {
    const val EXTRA_REPLY = "com.example.android.peopleListsql.REPLY"
  }
}
