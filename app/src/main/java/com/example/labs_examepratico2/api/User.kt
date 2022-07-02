package com.example.labs_examepratico2.api

import com.google.android.material.internal.NavigationMenu

/*data class User (
  val id: Int,
  val name: String,
  val email: String,
  val address: Address
)*/

/*data class Address (
  val city: String,
  val zipcode: String
)*/

data class OutputPost (
  val id: Int,
  val title: String
)

data class User(
  val name: NAME,
  val currencies: CURRENCIES,
  val capital: List<String>
)

data class NAME (
  val nativeName: NATIVENAME
  )

data class NATIVENAME (
  val por: POR
)

data class POR (
  val common: String
)

data class CURRENCIES (
  val EUR: eur
)

data class eur (
  val symbol: String
)
