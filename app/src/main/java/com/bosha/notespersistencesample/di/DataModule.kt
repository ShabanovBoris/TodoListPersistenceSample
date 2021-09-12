package com.bosha.notespersistencesample.di


import com.bosha.notespersistencesample.data.db.NotesLocalDataSource
import com.bosha.notespersistencesample.data.db.impl.NotesLocalDataSourceImpl
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

    @ScreenScope
    @Binds
    fun provideNotesLocalDataSource(
        notesLocalDataSourceImpl: NotesLocalDataSourceImpl
    ): NotesLocalDataSource
}