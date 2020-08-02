package com.sushant.notes.notestracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sushant.notes.database.Notes
import com.sushant.notes.databinding.GridItemViewBinding

class NotesAdapter(private val clickListener: NotesListener) : ListAdapter<Notes, NotesAdapter.ViewHolder>(NotesDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position)!!)
    }
    class ViewHolder private constructor(val binding : GridItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: NotesListener, item : Notes) {
            binding.notes = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GridItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder((binding))
            }
        }
    }
    class NotesDiffCallback : DiffUtil.ItemCallback<Notes>() {
        override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem.noteId == newItem.noteId
        }

        override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem == newItem
        }
    }
    class NotesListener(val clickListener : (noteId : Long) -> Unit) {
        fun onClick(note : Notes) = clickListener(note.noteId)
    }

}