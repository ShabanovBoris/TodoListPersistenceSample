package com.bosha.notespersistencesample.domain.repositories

import com.bosha.notespersistencesample.domain.common.NotesResult
import com.bosha.notespersistencesample.domain.entities.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun getNotesCache(): Flow<NotesResult>

    suspend fun insertNotesCache(list: List<Note>)

    suspend fun insertNoteCache(note: Note)

    suspend fun clearCache()

    suspend fun deleteFromCache(noteId: String)

}