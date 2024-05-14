package com.novatech.notekeeper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.novatech.notekeeper.model.Note
import com.novatech.notekeeper.repository.NoteRepository
import kotlinx.coroutines.launch

// gotto extend from android view model that this class will act as a view model for this application
class NoteViewModel(
    app: Application,
    private val noteRepository: NoteRepository
) : AndroidViewModel(app) {

    fun addNote(note: Note) = viewModelScope.launch {
        noteRepository.insertNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }

    fun getALlNotes() = noteRepository.getAllNotes()

    fun searchNote(query: String) = noteRepository.searchNote(query)

}