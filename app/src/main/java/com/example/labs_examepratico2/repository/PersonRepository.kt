package com.example.labs_examepratico2.repository

import androidx.annotation.WorkerThread
import com.example.labs_examepratico2.dao.PersonDao
import com.example.labs_examepratico2.model.Person
import kotlinx.coroutines.flow.Flow

class PersonRepository(private val personDao: PersonDao) {
  val allPeople: Flow<List<Person>> = personDao.getOrderedPeople()

  @Suppress ("RedundantSuspendModifier")
  @WorkerThread
  suspend fun insert (person: Person) {
    personDao.insert(person)
  }
}
