package com.simon.notepad

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.simon.notepad.ui.MainActivityViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


const val EDIT_NOTEPAD_REQUEST_CODE = 100

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initViews()
        initViewModel()

    }

    private fun initViews() {
        fab.setOnClickListener {
            val intent = Intent(this, ActivityEdit::class.java)
            intent.putExtra(ActivityEdit.EXTRA_NOTE, mainActivityViewModel.note.value)
            startActivity(intent)
        }
    }


    private fun initViewModel(){
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        mainActivityViewModel.note.observe(this, Observer { note ->
            if (note != null) {
                tvTitle.text = note.title
                tvDate.text = getString(R.string.last_updated, note.lastUpdated.toString())
                tvContents.text = note.content
            }
        })
    }

    private fun startEditActivity(){
        val intent = Intent(this, ActivityEdit::class.java)
        startActivityForResult(intent, EDIT_NOTEPAD_REQUEST_CODE)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
