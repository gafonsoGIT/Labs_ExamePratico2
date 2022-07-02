package com.example.labs_examepratico2.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.labs_examepratico2.model.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

  @Query("SELECT * FROM person_table ORDER BY nome ASC")
  fun getOrderedPeople(): Flow<List<Person>>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insert(person: Person)

  @Query("DELETE FROM person_table")
  suspend fun deleteAll()

  @Query("DELETE FROM person_table where idade > 20")
  suspend fun delete20plus()

  @Query("SELECT * FROM person_table where nome LIKE :search")
  fun getNameStartB(search:String): LiveData<List<Person>>

}
