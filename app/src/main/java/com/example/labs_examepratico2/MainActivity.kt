package com.example.labs_examepratico2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labs_examepratico2.adapter.UserAdapter
import com.example.labs_examepratico2.api.EndPoints
import com.example.labs_examepratico2.api.OutputPost
import com.example.labs_examepratico2.api.ServiceBuilder
import com.example.labs_examepratico2.api.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      val request = ServiceBuilder.buildService(EndPoints::class.java)
      val call = request.getCountryByName()

      call.enqueue(object : Callback<List<User>> {
        override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
          if(response.isSuccessful) {
            val recView:RecyclerView = findViewById(R.id.rv1)
            recView.layoutManager = LinearLayoutManager(this@MainActivity)
            recView.adapter = UserAdapter(response.body()!!)
          }
        }
        override fun onFailure(call: Call<List<User>>, t: Throwable) {
          Toast.makeText(this@MainActivity,"${t.message}", Toast.LENGTH_SHORT).show()
        }
      })
    }

    /*fun getSingle(view: View) {
      val request = ServiceBuilder.buildService(EndPoints::class.java)
      val call = request.getCountryByName("portugal")
      call.enqueue(object : Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
          if (response.isSuccessful) {
            print("ok")
            val c: User = response.body()!!
            Toast.makeText(this@MainActivity, c.name.nativeName.por.common + " - " + c.currencies.EUR.symbol + " - " + c.capital, Toast.LENGTH_SHORT).show()
          }
        }

        override fun onFailure(call: Call<User>, t: Throwable) {
          Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
        }
      })
    }*/

    /*fun post(view: View) {
      val request = ServiceBuilder.buildService(EndPoints::class.java)
      val call = request.postTest("Teste Lat_Retrofit")
      call.enqueue(object: Callback<OutputPost> {
        override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {
          if(response.isSuccessful) {
            val c : OutputPost = response.body()!!
            Toast.makeText(this@MainActivity, c.id.toString() + " - " + c.title, Toast.LENGTH_SHORT).show()
          }
        }

        override fun onFailure(call: Call<OutputPost>, t: Throwable) {
          Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
        }
      })
    }*/
}
