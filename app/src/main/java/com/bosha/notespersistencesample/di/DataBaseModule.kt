package com.bosha.notespersistencesample.di

import android.content.Context
import androidx.room.Room
import com.bosha.notespersistencesample.data.db.DbContract
import com.bosha.notespersistencesample.data.db.room.NotesRoomDao
import com.bosha.notespersistencesample.data.db.room.NotesRoomDataBase
import com.bosha.notespersistencesample.data.db.sqllite.NotesOpenHelperDao
import com.bosha.notespersistencesample.di.scopes.ScreenScope
import dagger.Module
import dagger.Provides

@Module
class DataBaseModule {

    @ScreenScope
    @Provides
    fun provideNotesDataBase(appContext: Context): NotesRoomDataBase =
        Room.databaseBuilder(
            appContext,
            NotesRoomDataBase::class.java,
            DbContract.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @ScreenScope
    @Provides
    fun provideNotesRoomDao(notesDataBase: NotesRoomDataBase): NotesRoomDao =
        notesDataBase.notesDao()


    @ScreenScope
    @Provides
    fun provideNotesOpenHelperDao(appContext: Context): NotesOpenHelperDao =
        NotesOpenHelperDao(appContext)
}