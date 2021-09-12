package com.bosha.notespersistencesample.di

import com.bosha.notespersistencesample.domain.interactors.AddEditNotesInteractor
import com.practice.domain.interactors.DeleteNotesInteractor
import com.bosha.notespersistencesample.domain.interactors.GetCachedNotesUseCase
import com.bosha.notespersistencesample.domain.repositories.NotesRepository
import com.bosha.notespersistencesample.di.scopes.ScreenScope
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @ScreenScope
    @Provides
    fun provideAddEditNotesInteractor(repository: NotesRepository): AddEditNotesInteractor =
        AddEditNotesInteractor(repository)

    @ScreenScope
    @Provides
    fun provideDeleteNotesInteractor(repository: NotesRepository): DeleteNotesInteractor =
        DeleteNotesInteractor(repository)

    @ScreenScope
    @Provides
    fun provideGetNotesInteractor(repository: NotesRepository): GetCachedNotesUseCase =
        GetCachedNotesUseCase(repository)
}