package com.bosha.notespersistencesample.di


import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.bosha.notespersistencesample.data.db.room.NotesRoomDao
import com.bosha.notespersistencesample.data.db.room.impl.NotesLocalDataStoreRoom
import com.bosha.notespersistencesample.data.db.sqllite.NotesOpenHelperDao
import com.bosha.notespersistencesample.data.db.sqllite.impl.NotesLocalDataStoreOpenHelper
import com.bosha.notespersistencesample.data.mappers.NotesEntityMapper
import com.bosha.notespersistencesample.data.reposetories.NotesRepositoryMediator
import com.bosha.notespersistencesample.data.reposetories.RepositoryFactory
import com.bosha.notespersistencesample.di.scopes.ScreenScope
import com.bosha.notespersistencesample.domain.common.DISPATCHER_IO
import com.bosha.notespersistencesample.domain.repositories.NotesRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named

@Module
class DataModule {

    @Provides
    fun roomDataStoreProvider(
        dao: NotesRoomDao,
        @Named(DISPATCHER_IO)
        dispatcher: CoroutineDispatcher,
        notesEntityMapper: NotesEntityMapper
    ): Lazy<NotesLocalDataStoreRoom> =
        lazy { NotesLocalDataStoreRoom(dao, dispatcher, notesEntityMapper) }

    @Provides
    fun openHelperDataStoreProvider(
        dao: NotesOpenHelperDao,
        @Named(DISPATCHER_IO)
        dispatcher: CoroutineDispatcher,
        notesEntityMapper: NotesEntityMapper
    ): Lazy<NotesLocalDataStoreOpenHelper> =
        lazy { NotesLocalDataStoreOpenHelper(dao, dispatcher, notesEntityMapper) }

    @ScreenScope
    @Provides
    fun provideRepository(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        factory: RepositoryFactory
    ): NotesRepository =
        NotesRepositoryMediator(context, lifecycleOwner, factory)
}