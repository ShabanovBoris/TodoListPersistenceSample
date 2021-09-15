package com.bosha.notespersistencesample.data.mappers

import com.bosha.notespersistencesample.data.dto.NoteEntity
import com.bosha.notespersistencesample.domain.entities.Note
import javax.inject.Inject

class NotesEntityMapper @Inject constructor() {
    private fun NoteEntity.toNote(): Note =
        Note(
            title = title,
            colorId = color,
            createDate = date,
            id = id.toString(),
            description = description,
            priority = priority,
            type = type,
        )

    fun toNotesList(list: List<NoteEntity>): List<Note> =
        list.map { it.toNote() }

    fun Note.toNoteEntity(): NoteEntity =
        NoteEntity(
            id = id.takeIf { it.isNotEmpty() }?.toInt() ?: 0,
            title = title,
            color = colorId,
            date = createDate,
            description = description,
            priority = priority,
            type = type
        )

    fun toNotesEntityList(list: List<Note>): List<NoteEntity> =
        list.map { it.toNoteEntity() }
}