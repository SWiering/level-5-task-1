package com.simon.notepad.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.simon.notepad.db.NoteRepository

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository = NoteRepository(application.applicationContext)

    val note = noteRepository.getNotepad()

}
