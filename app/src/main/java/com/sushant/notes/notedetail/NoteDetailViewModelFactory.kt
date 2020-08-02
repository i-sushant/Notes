package com.sushant.notes.notedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sushant.notes.database.NotesDao

@Suppress("UNCHECKED_CAST")
class NoteDetailViewModelFactory(private val noteKey : Long, private val dataSource : NotesDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom((NoteDetailViewModel::class.java))) {
            return NoteDetailViewModel(noteKey,dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}