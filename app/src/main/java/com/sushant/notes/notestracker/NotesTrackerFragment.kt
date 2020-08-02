package com.sushant.notes.notestracker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sushant.notes.R
import com.sushant.notes.database.NotesDatabase
import com.sushant.notes.databinding.NotesTrackerFragmentBinding


class NotesTrackerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : NotesTrackerFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.notes_tracker_fragment, container, false
        )
        val application = requireNotNull(this.activity).application
        val dataSource  = NotesDatabase.getInstance(application)!!.notesDatabaseDao
        val notesTrackerViewModel = NotesTrackerViewModel(dataSource, application)
        binding.notesTrackerViewModel = notesTrackerViewModel
        binding.lifecycleOwner = this
        notesTrackerViewModel.showNotesDetailPage.observe(viewLifecycleOwner, Observer {
            if(it == true){
                this.findNavController().navigate(NotesTrackerFragmentDirections.actionNotesTrackerFragmentToCreateNoteFragment())
                notesTrackerViewModel.doneNavigating()
            }
        })
        notesTrackerViewModel.navigateToNotesDataPage.observe(viewLifecycleOwner, Observer { noteId ->
            noteId?.let {
                this.findNavController().navigate(NotesTrackerFragmentDirections.actionShowDetail(noteId))
                notesTrackerViewModel.onNavigatedToDetailsFragment()
            }
        })
        val manager = GridLayoutManager(activity,2)
        binding.notesList.layoutManager = manager
        val adapter = NotesAdapter(NotesListener { noteId ->
            notesTrackerViewModel.showSelectedItem(noteId)
        })
        binding.notesList.adapter = adapter

        notesTrackerViewModel.notes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        return binding.root
    }
}