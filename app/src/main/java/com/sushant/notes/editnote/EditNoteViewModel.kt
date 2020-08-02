package com.sushant.notes.editnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sushant.notes.database.Notes
import com.sushant.notes.database.NotesDao
import kotlinx.coroutines.*

class EditNoteViewModel(dataSource : NotesDao) : ViewModel() {
    val database = dataSource
    private var _post_title = MutableLiveData<String>()
    val post_title : LiveData<String>
            get() = _post_title
    private var _post_description = MutableLiveData<String>()
    val post_description : LiveData<String>
        get() = _post_description
    private var _navigate_to_notestracker = MutableLiveData<Boolean>()
    val navigate_to_notestracker : LiveData<Boolean>
        get() = _navigate_to_notestracker
    private var currentNote = MutableLiveData<Notes?>()
    private val viewModelJob = Job()
    private val uiScope =  CoroutineScope(Dispatchers.Main + viewModelJob)
    private suspend fun insert(note : Notes) {
        withContext(Dispatchers.IO) {
            database.insert(note)
        }
    }
    private suspend fun getCurrentNoteFromDatabase() : Notes? {
        return withContext(Dispatchers.IO) {
            val currNote = database.getCurrentNote()
            currNote
        }
    }
    private var _save_post = MutableLiveData<Boolean>()
    val save_post : LiveData<Boolean?>
    get() = _save_post
    fun onSaveClickListener() {
        _save_post.value = true
    }
    fun onSaveClicked(post_title : String, post_description: String) {
        _post_title.value = post_title
        _post_description.value = post_description
        savePost()

    }
    private fun savePost() {
        uiScope.launch {
            val note = Notes()
            note.note_info = _post_description.value.toString()
            note.note_title = _post_title.value.toString()
            insert(note)
//            currentNote.value = getCurrentNoteFromDatabase()
        }
        navigateToNotesTracker()
    }
    fun navigateToNotesTracker() {
        _navigate_to_notestracker.value = true;
    }
    override fun onCleared() {

        super.onCleared()
        viewModelJob.cancel()
    }
}