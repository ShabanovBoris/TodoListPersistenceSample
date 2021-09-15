package com.bosha.notespersistencesample.domain.interactors

import com.bosha.notespersistencesample.domain.entities.Note
import com.bosha.notespersistencesample.domain.repositories.NotesRepository

class AddEditNotesInteractor(
    private val repository: NotesRepository
) {
    suspend fun insertNotesCache(list: List<Note>) =
        repository.insertNotesCache(list)

    suspend fun insertNoteCache(note: Note) =
        repository.insertNoteCache(note)
}