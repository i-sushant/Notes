package com.sushant.notes.createnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sushant.notes.R
import com.sushant.notes.database.Notes
import com.sushant.notes.database.NotesDao
import kotlinx.coroutines.*
import kotlin.random.Random

class CreateNoteViewModel(dataSource : NotesDao) : ViewModel() {
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
            val rand = Random(System.nanoTime())
            val color = when(rand.nextInt(5 - 1 + 1) + 1) {
                1 -> R.drawable.rounded_corner_lemon
                2 -> R.drawable.rounded_corner_lemon_dark
                3 -> R.drawable.rounded_corner_violet
                4 -> R.drawable.rounded_corner_orange_warm
                5 -> R.drawable.rounded_corner_brown_light
                6 -> R.drawable.rounded_corner_blue
                7 -> R.drawable.rounded_corner_aqua
                8 -> R.drawable.rounded_corner_pink
                else -> -1
            }
            note.color = color
            insert(note)
        }
        navigateToNotesTracker()
    }
    fun navigateToNotesTracker() {
        _navigate_to_notestracker.value = true
    }
    fun navigateToNotesTrackerCompleted() {
        _navigate_to_notestracker.value = false
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}