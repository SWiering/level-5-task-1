package com.simon.notepad.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "Note")
data class Note(

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "last_updated")
    var lastUpdated: Date,

    @ColumnInfo(name = "content")
    var content: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

) : Parcelable