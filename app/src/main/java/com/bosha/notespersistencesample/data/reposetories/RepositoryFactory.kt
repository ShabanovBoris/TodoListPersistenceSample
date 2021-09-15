package com.bosha.notespersistencesample.data.reposetories


import com.bosha.notespersistencesample.data.db.room.impl.NotesLocalDataStoreRoom
import com.bosha.notespersistencesample.data.db.sqllite.impl.NotesLocalDataStoreOpenHelper
import com.bosha.notespersistencesample.domain.common.OPEN_HELPER_DATASTORE
import com.bosha.notespersistencesample.domain.common.ROOM_DATASTORE
import com.bosha.notespersistencesample.domain.repositories.NotesRepository
import javax.inject.Inject

class RepositoryFactory @Inject constructor(
    private val room: Lazy<NotesLocalDataStoreRoom>,
    private val openHelper: Lazy<NotesLocalDataStoreOpenHelper>
) {

    fun create(type: String): NotesRepository {
        return when (type) {
            ROOM_DATASTORE -> NotesRepositoryImpl(room.value)

            OPEN_HELPER_DATASTORE -> NotesRepositoryImpl(openHelper.value)

            else -> error("Wrong argument RepositoryFactory")
        }
    }
}