package com.bosha.notespersistencesample.data.db


import androidx.room.*
import com.bosha.notespersistencesample.data.dto.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes_table ORDER BY date DESC")
    fun getNotes(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(list: List<NoteEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Query("DELETE FROM notes_table")
    suspend fun clear()

    @Query("DELETE FROM notes_table WHERE notes_table_id = :id")
    suspend fun delete(id: String)
}