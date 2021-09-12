package com.bosha.notespersistencesample.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bosha.notespersistencesample.data.dto.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NotesDataBase: RoomDatabase() {

    abstract fun notesDao(): NotesDao

}