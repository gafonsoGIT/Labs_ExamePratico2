package com.example.labs_examepratico2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "person_table")
class Person (
  @PrimaryKey
  @ColumnInfo(name = "nome")
  val nome: String,
  @ColumnInfo(name = "email")
  val email: String,
  @ColumnInfo(name = "idade")
  val idade: Int,
  @ColumnInfo(name = "anoNascimento")
  val anoNascimento: Int
  ) : Serializable {}
