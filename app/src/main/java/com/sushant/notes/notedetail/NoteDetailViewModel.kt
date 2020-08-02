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
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val note =  MediatorLiveData<Notes>()
    fun getNote() = note
    init {
        note.addSource(database.getNoteWithId(noteId), note::setValue)
    }

    private val _navigateToNoteTracker = MutableLiveData<Boolean?>()
    val navigateToNoteTracker: LiveData<Boolean?>
        get() = _navigateToNoteTracker

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    private suspend fun delete() {
        withContext(Dispatchers.IO) {
            database.deleteNoteById(noteId)
        }
    }
    private fun deleteNoteById() {
        uiScope.launch {
            delete()
        }
        navigateToNotesTracker()
    }
    fun deleteNote() {
        deleteNoteById()
    }
    fun navigateToNotesTracker() {
        _navigateToNoteTracker.value = true
    }
    fun doneNavigating() {
        _navigateToNoteTracker.value = null
    }

}