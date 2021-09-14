package com.bosha.notespersistencesample.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bosha.notespersistencesample.data.db.DbContract

@Entity(tableName = DbContract.TABLE_NAME)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbContract.COLUMN_ID)
    val id: Int = 0,
    @ColumnInfo(name = DbContract.TITLE)
    val title: String,
    @ColumnInfo(name = DbContract.COLOR_RES)
    val color: Int,
    @ColumnInfo(name = DbContract.DATE)
    val date: Long,
    @ColumnInfo(name = DbContract.DESCRIPTION)
    val description: String,
    @ColumnInfo(name = DbContract.PRIORITY)
    val priority: Int,
    @ColumnInfo(name = DbContract.TYPE)
    val type: Int,
)





