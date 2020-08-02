package com.sushant.notes.notestracker

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sushant.notes.database.Notes
import com.sushant.notes.database.NotesDao
import kotlinx.coroutines.*

class NotesTrackerViewModel(val database: NotesDao, application : Application) {

    val notes = database.getAllNotes()

//    private val _navigateToNotesPage= MutableLiveData<Notes>()
//    val navigateToNotesPage : LiveData<Notes>
//        get() = _navigateToNotesPage
    private var _navigateToNoteDataPage= MutableLiveData<Long>()
    val navigateToNotesDataPage : LiveData<Long>
        get() = _navigateToNoteDataPage
    private val _showNotesDetailPage = MutableLiveData<Boolean>()
    val showNotesDetailPage : LiveData<Boolean>
        get() = _showNotesDetailPage
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun onClear() {
        uiScope.launch {
            clear()

        }
    }
    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }
//    fun onNoteClicked(noteId : Long) {
//        _navigateToNoteDataPage.value = noteId
//    }
    fun showSelectedItem(noteId : Long) {
        _navigateToNoteDataPage.value = noteId
    }
    fun onStartNotemaking() {
        _showNotesDetailPage.value = true
    }
    fun doneNavigating() {
        _showNotesDetailPage.value = false
    }

}