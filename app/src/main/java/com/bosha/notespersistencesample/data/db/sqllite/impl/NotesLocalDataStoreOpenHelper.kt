package com.bosha.notespersistencesample.data.db.sqllite.impl

import com.bosha.notespersistencesample.data.db.NotesLocalDataStore
import com.bosha.notespersistencesample.data.db.sqllite.NotesOpenHelperDao
import com.bosha.notespersistencesample.data.mappers.NotesEntityMapper
import com.bosha.notespersistencesample.data.utils.logError
import com.bosha.notespersistencesample.domain.common.DISPATCHER_IO
import com.bosha.notespersistencesample.domain.entities.Note
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Named

class NotesLocalDataStoreOpenHelper @Inject constructor(
    private val dao: NotesOpenHelperDao,
    @param: Named(DISPATCHER_IO)
    private val dispatcher: CoroutineDispatcher,
    private val notesEntityMapper: NotesEntityMapper
) : NotesLocalDataStore {
    override suspend fun getNotes(): Flow<List<Note>> =
        flow {
            dao.dataFlow.collect {
                emit(it)
            }
        }
            .catch { logError(currentCoroutineContext(), it, this) }
            .map { notesEntityMapper.toNotesList(it) }
            .flowOn(dispatcher)


    override suspend fun insertNotes(list: List<Note>) {

    }

    override suspend fun clear() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(noteId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun insertNote(note: Note) {
        notesEntityMapper.apply {
            dao.insertNote(note.toNoteEntity())
        }
    }
}