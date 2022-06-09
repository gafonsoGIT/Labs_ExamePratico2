package com.example.labs_examepratico2.api

import retrofit2.Call
import retrofit2.http.*

interface EndPoints {

  @GET("/name")
  fun getCountries(): Call<List<User>>

  @GET("/name/{common}")
  fun getCountryByName(@Path("common") common: String): Call<User>

  @FormUrlEncoded
  @POST("/posts")
  fun postTest(@Field("title") title: String?): Call<OutputPost>
}


