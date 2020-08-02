package com.sushant.notes.notedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sushant.notes.database.Notes
import com.sushant.notes.database.NotesDao
import kotlinx.coroutines.*

class NoteDetailViewModel(private val noteId : Long = 0L, dataSource : NotesDao) : ViewModel() {
    val database = dataSource
    private val viewModelJob = Job()

    private val note =  MediatorLiveData<Notes>()
    fun getNote() = note
    init {
        note.addSource(database.getNoteWithId(noteId), note::setValue)
    }
//    init {
//        note.addSource(database.getNoteWithId(noteId), note::setValue)
//    }
//    fun getNote() : LiveData<Notes> {
//        note = database.getNoteWithId(noteId)
//
//    }
    private val _navigateToNoteTracker = MutableLiveData<Boolean?>()
    val navigateToNoteTracker: LiveData<Boolean?>
        get() = _navigateToNoteTracker

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    fun navigateToNotesTracker() {
        _navigateToNoteTracker.value = true
    }
    fun doneNavigating() {
        _navigateToNoteTracker.value = null
    }

}