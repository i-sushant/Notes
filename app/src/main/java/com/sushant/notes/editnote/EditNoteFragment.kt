package com.sushant.notes.editnote

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sushant.notes.R
import com.sushant.notes.database.NotesDatabase
import com.sushant.notes.databinding.EditNoteFragmentBinding

class EditNoteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : EditNoteFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.edit_note_fragment, container, false
        )
        val application = requireNotNull(this.activity).application
        val dataSource  = NotesDatabase.getInstance(application)!!.notesDatabaseDao
        val editViewModel = EditNoteViewModel(dataSource)
        binding.editViewModel = editViewModel
        binding.lifecycleOwner = this
        editViewModel.save_post.observe(viewLifecycleOwner, Observer {
            Log.d("EditNoteFragment", binding.postTitle.text.toString())
            if(binding.postTitle.text.isNotEmpty())
                editViewModel.onSaveClicked(binding.postTitle.text.toString(), binding.postDescription.text.toString())
        })
        editViewModel.navigate_to_notestracker.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                this.findNavController().navigate(
                    EditNoteFragmentDirections.actionEditNoteFragmentToNotesTrackerFragment())
            }
        })
        return binding.root
    }


}