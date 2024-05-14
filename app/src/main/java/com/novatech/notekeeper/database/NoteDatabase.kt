package com.novatech.notekeeper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.novatech.notekeeper.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase(){

    abstract fun getNoteDao() : NoteDao

    companion object {

        // marks the JVM backing field of the ann
        // writes to this instance would be immediately made available to other threads
        @Volatile
        private var instance : NoteDatabase? = null
        // Any is a function that is added as an extension to iterable and map interfaces which take
        // a higher order function as param to predicate the condition and return boolean as true
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK) {
            // synchronized is used in multi-threading
            // ensures only one instance of the DB
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_db"
        ).build()

    }

}