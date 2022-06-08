package com.example.labs_examepratico2.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.labs_examepratico2.model.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

  @Query("SELECT * FROM person_table ORDER BY name")
  fun getOrderedPeople(): Flow<List<Person>>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insert(person: Person)

  @Query("DELETE FROM person_table")
  suspend fun deleteAll()

}
