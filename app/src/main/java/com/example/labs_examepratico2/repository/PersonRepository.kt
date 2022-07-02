package com.example.labs_examepratico2.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.labs_examepratico2.dao.PersonDao
import com.example.labs_examepratico2.model.Person
import kotlinx.coroutines.flow.Flow

class PersonRepository(private val personDao: PersonDao) {
  val allPeople: Flow<List<Person>> = personDao.getOrderedPeople()
  val allPeoplewithB: LiveData<List<Person>> = personDao.getNameStartB("B%");

  @Suppress ("RedundantSuspendModifier")
  @WorkerThread
  suspend fun insert (person: Person) {
    personDao.insert(person)
  }

  @Suppress ("RedundantSuspendModifier")
  @WorkerThread
  suspend fun delete20plus () {
    personDao.delete20plus()
  }
}
