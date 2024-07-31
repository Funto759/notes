package com.example.notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notes.Util.DateRoomConverter
import com.example.notes.Notes


@Database(
    entities = [Notes::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(DateRoomConverter::class)
abstract class NoteDatabase  : RoomDatabase()  {

    abstract fun getNoteDao(): NoteDao

    companion object {
        @Volatile
        private var instance: NoteDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK) {
            instance?: getDatabase(context).also {
                instance = it
            }
        }

       private fun getDatabase(context: Context) =
           Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).fallbackToDestructiveMigration().build()

    }
    }

