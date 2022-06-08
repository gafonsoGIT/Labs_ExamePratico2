package com.example.labs_examepratico2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class NewPersonActivity : AppCompatActivity() {

  private lateinit var editPersonView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_person)

        editPersonView = findViewById(R.id.edit_person)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
          val replyIntent = Intent()
          if(TextUtils.isEmpty(editPersonView.text)) {
            setResult(Activity.RESULT_CANCELED, replyIntent)
          }
          else {
            val people = editPersonView.text.toString()
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
