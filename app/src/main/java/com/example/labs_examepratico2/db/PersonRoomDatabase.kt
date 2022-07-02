package com.example.labs_examepratico2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.labs_examepratico2.dao.PersonDao
import com.example.labs_examepratico2.model.Person
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Person::class], version=1, exportSchema = false)
abstract class PersonRoomDatabase: RoomDatabase() {

  abstract fun personDao(): PersonDao

  private class PersonDatabaseCallback(
    private val scope: CoroutineScope
  ) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
      super.onCreate(db)
      INSTANCE?.let { database ->
        scope.launch {
          populateDatabase(database.personDao())
        }
      }
    }

    suspend fun populateDatabase(personDao: PersonDao) {
      //Eliminar Tudo
      personDao.deleteAll()

      //Adicionar pessoas
      var person = Person("Goncalo1","goncalo1@ipvc.pt",21,2001)
      personDao.insert(person)

      person = Person("Miguel1", "miguel@ipvc.pt",22,2000)
      personDao.insert(person)

      person = Person("Beatriz", "beatriz@ipvc.pt",20,2002)
      personDao.insert(person)

      person = Person("Bolota1", "bolota@ipvc.pt",25,1997)
      personDao.insert(person)

      person = Person("Teste", "teste@ipvc.pt",20,2001)
      personDao.insert(person)

      person = Person("Bruno","abc@teste.com",23,1998)
      personDao.insert(person)
    }
  }

  companion object {

    @Volatile
    private var INSTANCE: PersonRoomDatabase? = null

    fun getDatabase(context: Context, scope: CoroutineScope): PersonRoomDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          PersonRoomDatabase::class.java,
          "person_database_2"
        )
          .addCallback(PersonDatabaseCallback(scope))
          .build()
        INSTANCE = instance
        instance
      }
    }
  }
}
