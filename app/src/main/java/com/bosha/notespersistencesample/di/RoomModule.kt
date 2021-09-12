package com.bosha.notespersistencesample.di

import android.content.Context
import androidx.room.Room
import com.bosha.notespersistencesample.data.db.DbContract
import com.bosha.notespersistencesample.data.db.NotesDao
import com.bosha.notespersistencesample.data.db.NotesDataBase
import com.bosha.notespersistencesample.di.scopes.ScreenScope
import dagger.Module
import dagger.Provides

@Module
class RoomModule {

    @ScreenScope
    @Provides
    fun provideNotesDataBase(appContext: Context): NotesDataBase =
        Room.databaseBuilder(
            appContext,
            NotesDataBase::class.java,
            DbContract.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @ScreenScope
    @Provides
    fun provideNotesDao(notesDataBase: NotesDataBase): NotesDao =
        notesDataBase.notesDao()
}