package com.practice.domain.interactors

import com.bosha.notespersistencesample.domain.repositories.NotesRepository

class DeleteNotesInteractor(
    private val repository: NotesRepository
) {

    suspend fun deleteNote(noteId: String) {
        repository.deleteFromCache(noteId)
    }

    suspend fun clearCache() = repository.clearCache()
}