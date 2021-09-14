package com.bosha.notespersistencesample.data.reposetories

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.bosha.notespersistencesample.data.utils.DataStorePreference
import com.bosha.notespersistencesample.domain.common.*
import com.bosha.notespersistencesample.domain.entities.Note
import com.bosha.notespersistencesample.domain.repositories.NotesRepository
import kotlinx.coroutines.flow.Flow

/**
 * this class has [delegate] that may be ROOM or OpenHelper implementations,
 * calls [switchDelegate] when preference value is changing
 */
class NotesRepositoryMediator(
    appContext: Context,
    lifecycleOwner: LifecycleOwner,
    private val factory: RepositoryFactory
) : NotesRepository {

    private var _delegate: NotesRepository? = null
    private val delegate get() = requireNotNull(_delegate)

    private val dataStorePreference by lazy {
        DataStorePreference(appContext, lifecycleOwner){
            switchDelegate(it)
        }
    }

    init {
        switchDelegate(dataStorePreference.dataPref)
    }

    private fun switchDelegate(value: String) {
        when (value) {
            ROOM_DATASTORE -> {
                _delegate = factory.create(ROOM_DATASTORE)
            }
            OPEN_HELPER_DATASTORE -> {
                _delegate = factory.create(OPEN_HELPER_DATASTORE)
            }
        }
    }

    override suspend fun getNotesCache(): Flow<NotesResult> {
        return delegate.getNotesCache()
    }

    override suspend fun insertNotesCache(list: List<Note>) {
        delegate.insertNotesCache(list)
    }

    override suspend fun insertNoteCache(note: Note) {
        delegate.insertNoteCache(note)
    }

    override suspend fun clearCache() {
        delegate.clearCache()
    }

    override suspend fun deleteFromCache(noteId: String) {
        delegate.deleteFromCache(noteId)
    }

}