package com.sushant.notes.createnote

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
import com.sushant.notes.databinding.CreateNoteFragmentBinding


class CreateNoteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : CreateNoteFragmentBinding= DataBindingUtil.inflate(
            inflater, R.layout.create_note_fragment, container, false
        )
        val application = requireNotNull(this.activity).application
        val dataSource  = NotesDatabase.getInstance(application)!!.notesDatabaseDao
        val createViewModel = CreateNoteViewModel(dataSource)
        binding.createViewModel = createViewModel
        binding.lifecycleOwner = this
        createViewModel.save_post.observe(viewLifecycleOwner, Observer {
            if(binding.postTitle.text.isNotEmpty()) {
                createViewModel.onSaveClicked(binding.postTitle.text.toString(), binding.postDescription.text.toString())
                createViewModel.navigateToNotesTrackerCompleted()
            }
        })
        createViewModel.navigate_to_notestracker.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                this.findNavController().navigate(CreateNoteFragmentDirections.actionCreateNoteFragmentToNotesTrackerFragment())
                createViewModel.navigateToNotesTrackerCompleted()
            }
        })
        return binding.root
    }


}