package com.bosha.notespersistencesample.domain.common

import com.bosha.notespersistencesample.domain.entities.Note


sealed class NotesResult {
    data class ValidResult(
        val doList: List<Note>?,
        val doingList: List<Note>?,
        val doneList: List<Note>?
    ) : NotesResult()
    object EmptyResult : NotesResult()
    object EmptySearch: NotesResult()
}