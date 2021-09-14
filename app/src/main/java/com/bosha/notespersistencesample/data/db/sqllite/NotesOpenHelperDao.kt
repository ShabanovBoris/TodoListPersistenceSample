package com.bosha.notespersistencesample.data.db.sqllite

import android.content.Context
import com.bosha.notespersistencesample.data.db.DbContract
import com.bosha.notespersistencesample.data.dto.NoteEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NotesOpenHelperDao(appContext: Context) {

    private val db = NotesSQLiteDBHelper(appContext)

    private val _flow = MutableStateFlow(db.getNotes())
    val dataFlow get() = _flow.asStateFlow()

    fun insertNote(noteEntity: NoteEntity){
        db.insertNote(noteEntity)
        _flow.value = db.getNotes()
    }
}