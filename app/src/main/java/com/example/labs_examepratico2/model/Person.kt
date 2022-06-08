package com.example.labs_examepratico2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_table")
class Person (

  @PrimaryKey
  @ColumnInfo(name = "name")
  val name: String
  )
