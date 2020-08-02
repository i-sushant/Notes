package com.sushant.notes.notedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStore
import androidx.navigation.fragment.findNavController
import com.sushant.notes.R
import com.sushant.notes.database.NotesDatabase
import com.sushant.notes.databinding.NoteDetailFragmentBinding

class NoteDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : NoteDetailFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.note_detail_fragment, container, false )
        val application = requireNotNull(this.activity).application
        val arguments = NoteDetailFragmentArgs.fromBundle(requireArguments())
        val dataSource = NotesDatabase.getInstance(application)!!.notesDatabaseDao
        val viewModelFactory = NoteDetailViewModelFactory(arguments.noteId, dataSource)

        val noteDetailViewModel = ViewModelProvider(this, viewModelFactory).get(NoteDetailViewModel::class.java)
        binding.noteDetailViewModel = noteDetailViewModel
        binding.lifecycleOwner = this
        noteDetailViewModel.navigateToNoteTracker.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                this.findNavController().navigate(
                    NoteDetailFragmentDirections.actionNoteDetailFragmentToNotesTrackerFragment()
                )
                noteDetailViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}