package com.bosha.notespersistencesample.data.db

import com.bosha.notespersistencesample.domain.entities.Note
import kotlinx.coroutines.flow.Flow

interface NotesLocalDataSource {

    suspend fun getNotes(): Flow<List<Note>>

    suspend fun insertNotes(list: List<Note>)

    suspend fun clear()

    suspend fun deleteNote(noteId: String)

    suspend fun insertNote(note: Note)
}