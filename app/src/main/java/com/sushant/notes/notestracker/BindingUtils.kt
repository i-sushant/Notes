package com.sushant.notes.notestracker

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sushant.notes.database.Notes

@BindingAdapter("postTitle")
fun TextView.setPostTitle(item : Notes) {
    item.let {
        text = item.note_title
    }
}
@BindingAdapter("postDescription")
fun TextView.setPostDescription(item : Notes) {
    item.let {
        text = item.note_info
    }
}