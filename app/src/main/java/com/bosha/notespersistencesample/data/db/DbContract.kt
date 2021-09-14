package com.bosha.notespersistencesample.data.db

import android.provider.BaseColumns

internal object DbContract: BaseColumns {
    const val DATABASE_NAME = "notes.db"

    const val TABLE_NAME = "notes_table"
    const val TITLE = "title"
    const val COLOR_RES = "color_res"
    const val COLUMN_ID = TABLE_NAME + BaseColumns._ID
    const val DATE = "date"
    const val DESCRIPTION = "description"
    const val PRIORITY = "priority"
    const val TYPE = "type"
}