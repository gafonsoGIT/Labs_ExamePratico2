package com.example.labs_examepratico2.viewModel

import androidx.lifecycle.*
import com.example.labs_examepratico2.model.Person
import com.example.labs_examepratico2.repository.PersonRepository
import kotlinx.coroutines.launch

class PersonViewModel(private val repository: PersonRepository) : ViewModel() {

  val allPeople: LiveData<List<Person>> = repository.allPeople.asLiveData()

  fun insert(person: Person) = viewModelScope.launch {
    repository.insert(person)
  }
}

class PersonViewModelFactory(private val repository: PersonRepository): ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(PersonViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return PersonViewModel(repository) as T
    }
    throw IllegalAccessException("Unknown view model class")
  }
}
