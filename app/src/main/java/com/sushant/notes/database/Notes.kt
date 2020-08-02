package com.sushant.notes.database

import androidx.room.*

@Entity(tableName = "notes_table")
data class Notes (
    @PrimaryKey(autoGenerate = true)
    var noteId : Long = 0L,

    @ColumnInfo(name = "note_title")
    var note_title : String = "",

    @ColumnInfo(name = "note_info")
    var note_info : String = "",

    @ColumnInfo(name = "note_color")
    var color : Int = 0
)