package com.bosha.notespersistencesample.domain.interactors

import com.bosha.notespersistencesample.domain.common.NotesResult
import com.bosha.notespersistencesample.domain.repositories.NotesRepository
import kotlinx.coroutines.flow.*

class GetCachedNotesInteractor(
    private val repository: NotesRepository
) {

    suspend fun getNotesCache(): Flow<NotesResult> =
        repository.getNotesCache()

}