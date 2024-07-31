package com.example.notes.database

import com.example.notes.Notes

class NotesRepository(val db:NoteDatabase) {
    suspend fun upsert(note: Notes) = db.getNoteDao().upsertNote(note)


    suspend fun delete(note: Notes) = db.getNoteDao().deleteNotes(note)


    fun getNote() = db.getNoteDao().getNote()

    fun getNoteById(noteId : Long) = db.getNoteDao().getNoteById(noteId)

    fun getSearchNotes(searchQuery : String) = db.getNoteDao().searchNotes(searchQuery)

}