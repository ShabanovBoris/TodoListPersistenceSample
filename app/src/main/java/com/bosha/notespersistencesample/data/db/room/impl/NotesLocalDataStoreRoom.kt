package com.bosha.notespersistencesample.data.db.room.impl

import com.bosha.notespersistencesample.data.db.room.NotesRoomDao
import com.bosha.notespersistencesample.data.db.NotesLocalDataStore
import com.bosha.notespersistencesample.data.mappers.NotesEntityMapper
import com.bosha.notespersistencesample.data.utils.logError
import com.bosha.notespersistencesample.domain.common.DISPATCHER_IO
import com.bosha.notespersistencesample.domain.entities.Note
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class NotesLocalDataStoreRoom @Inject constructor(
    private val dao: NotesRoomDao,
    @param: Named(DISPATCHER_IO)
    private val dispatcher: CoroutineDispatcher,
    private val notesEntityMapper: NotesEntityMapper
) : NotesLocalDataStore {

    override suspend fun getNotes(): Flow<List<Note>> =
        flow { emitAll(dao.getNotes()) }
            .catch { logError(currentCoroutineContext(), it, this) }
            .map { notesEntityMapper.toNotesList(it) }
            .flowOn(dispatcher)


    override suspend fun insertNotes(list: List<Note>) = withContext(dispatcher) {
        dao.insertNotes(notesEntityMapper.toNotesEntityList(list))
    }

    override suspend fun clear() = dao.clear()

    override suspend fun deleteNote(noteId: String) = withContext(dispatcher) {
        dao.delete(noteId)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(notesEntityMapper.run { note.toNoteEntity() })
    }

}