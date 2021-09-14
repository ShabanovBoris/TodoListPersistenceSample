package com.bosha.notespersistencesample.data.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bosha.notespersistencesample.data.dto.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NotesRoomDataBase: RoomDatabase() {

    abstract fun notesDao(): NotesRoomDao

}