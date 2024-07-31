package com.example.notes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.Notes
import com.example.notes.database.NotesRepository
import kotlinx.coroutines.launch

class NotesViewModel (val notesRepository: NotesRepository): ViewModel(){




    fun saveNotes(notes: Notes){
        viewModelScope.launch {
            notesRepository.upsert(notes)
        }
    }

    fun deleteNotes(notes: Notes){
        viewModelScope.launch {
            notesRepository.delete(notes)
        }
    }

    fun getNotes() = notesRepository.getNote()

    fun getNotesById(noteId : Long) = notesRepository.getNoteById(noteId)

    fun searchNotes(searchQuery : String) = notesRepository.getSearchNotes(searchQuery)

    }
