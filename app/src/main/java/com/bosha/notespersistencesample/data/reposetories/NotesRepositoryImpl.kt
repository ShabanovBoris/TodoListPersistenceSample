package com.bosha.notespersistencesample.data.reposetories

import com.bosha.notespersistencesample.data.db.NotesLocalDataStore
import com.bosha.notespersistencesample.domain.common.NotesResult
import com.bosha.notespersistencesample.domain.common.ROOM_DATASTORE
import com.bosha.notespersistencesample.domain.entities.Note
import com.bosha.notespersistencesample.domain.repositories.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class NotesRepositoryImpl(
    private val localDataSource: NotesLocalDataStore
): NotesRepository {

   override suspend fun getNotesCache(): Flow<NotesResult> {
        return localDataSource.getNotes()
            .distinctUntilChanged()
            .map { notesList ->
                if (notesList.isEmpty()) {
                    return@map NotesResult.EmptyResult
                } else {
                    return@map NotesResult.ValidResult(
                        notesList.filter { it.type == Note.Type.DO.ordinal },
                        notesList.filter { it.type == Note.Type.DOING.ordinal },
                        notesList.filter { it.type == Note.Type.DONE.ordinal },
                    )
                }
            }

    }

    override suspend fun insertNotesCache(list: List<Note>) {
        localDataSource.insertNotes(list)
    }

    override suspend fun insertNoteCache(note: Note) {
        localDataSource.insertNote(note)
    }

    override suspend fun clearCache() {
        localDataSource.clear()
    }

    override suspend fun deleteFromCache(noteId: String) {
        return localDataSource.deleteNote(noteId)
    }
}