package com.bosha.notespersistencesample.di


import com.bosha.notespersistencesample.data.db.NotesLocalDataStore
import com.bosha.notespersistencesample.data.db.sqllite.impl.NotesLocalDataStoreOpenHelper
import com.bosha.notespersistencesample.data.reposetories.NotesRepositoryImpl
import com.bosha.notespersistencesample.domain.repositories.NotesRepository
import com.bosha.notespersistencesample.di.scopes.ScreenScope
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @ScreenScope
    @Binds
    fun provideRepository(
        repositoryImpl: NotesRepositoryImpl
    ): NotesRepository

//    @ScreenScope
//    @Binds
//    fun provideNotesLocalDataSource(
//        notesLocalDataSourceImpl: NotesDbRoomImpl
//    ): NotesDb

    @ScreenScope
    @Binds
    fun provideNotesLocalDataSource(
        notesLocalDataSourceImpl: NotesLocalDataStoreOpenHelper
    ): NotesLocalDataStore
}