package com.example.notes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.database.NotesRepository

class NotesViewModelProviderFactory(val notesRepository: NotesRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return com.example.notes.ui.NotesViewModel(notesRepository) as T
    }
}