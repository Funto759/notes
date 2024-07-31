package com.example.notes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.notes.Notes

@Dao
interface NoteDao {

    @Upsert
    suspend fun upsertNote(note: Notes) : Long


    @Query("SELECT * FROM notes")
     fun getNote(): LiveData<List<Notes>>

     @Query("SELECT * FROM notes WHERE id = :noteId ")
     fun getNoteById(noteId : Long): LiveData<Notes>

     @Query("SELECT * FROM notes WHERE title lIKE '%' || :searchQuery || '%' OR content LIKE '%' || :searchQuery || '%' ")
     fun searchNotes(searchQuery : String) : LiveData<List<Notes>>

    @Delete
    suspend fun deleteNotes(note: Notes) : Int
}