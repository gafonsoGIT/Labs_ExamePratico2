package com.example.labs_examepratico2.api

data class User (
  val id: Int,
  val name: String,
  val email: String,
  val address: Address
)

data class Address (
  val stress: String,
  val suite: String,
  val city: String,
  val zipcode: String,
  val geo: Geo
)

data class Geo (
  val lat: String,
  val lng: String,
)
