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
      personDao.deleteAll()

      var person = Person("Gonçalo")
      personDao.insert(person)
      person = Person("Afonso")
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
          "person_database_1"
        )
          .addCallback(PersonDatabaseCallback(scope))
          .build()
        INSTANCE = instance
        instance
      }
    }
  }
}
