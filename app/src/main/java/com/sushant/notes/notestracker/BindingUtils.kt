package com.sushant.notes.notestracker


import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.sushant.notes.R
import com.sushant.notes.database.Notes
import kotlin.random.Random


@BindingAdapter("postTitle")
fun TextView.setPostTitle(item : Notes?) {
    item?.let {
        text = item.note_title
    }
}
@BindingAdapter("postDescription")
fun TextView.setPostDescription(item : Notes?) {
    item?.let {
        text = item.note_info
    }
}
@BindingAdapter("postBackground")
fun TextView.setPostBackground(item : Notes?) {
    item?.let {
        background = ContextCompat.getDrawable(context, item.color)
    }
}

