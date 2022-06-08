package com.example.labs_examepratico2.api

data class User (
  val id: Int,
  val name: String,
  val email: String,
  val address: Address
)

data class Address (
  val city: String,
  val zipcode: String
)

data class OutputPost (
  val id: Int,
  val title: String
  )
