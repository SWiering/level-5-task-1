package com.simon.notepad.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.simon.notepad.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class NotepadRoomDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private const val DATABASE_NAME = "NOTEPAD_DATABASE"

        @Volatile
        private var notepadRoomDatabaseInstance: NotepadRoomDatabase? = null

        fun getDatabase(context: Context): NotepadRoomDatabase? {
            if (notepadRoomDatabaseInstance == null) {
                synchronized(NotepadRoomDatabase::class.java) {
                    if (notepadRoomDatabaseInstance == null) {
                        notepadRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            NotepadRoomDatabase::class.java, DATABASE_NAME
                        )
                        .fallbackToDestructiveMigration()
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                notepadRoomDatabaseInstance?.let { database ->
                                    CoroutineScope(Dispatchers.IO).launch {
                                        database.noteDao().insertNote(Note("Title", Date(), ""))
                                    }
                                }
                            }
                        })
                        .build()
                    }
                }
            }
            return notepadRoomDatabaseInstance
        }
    }

}