package com.bosha.notespersistencesample.data.db.sqllite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.bosha.notespersistencesample.data.db.DbContract
import com.bosha.notespersistencesample.data.dto.NoteEntity


private const val SQL_CREATE_ENTRIES =
    "CREATE TABLE ${DbContract.TABLE_NAME} (" +
            "${DbContract.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "${DbContract.TITLE} TEXT NOT NULL," +
            "${DbContract.COLOR_RES} INTEGER NOT NULL," +
            "${DbContract.DATE} INTEGER NOT NULL," +
            "${DbContract.DESCRIPTION} TEXT NOT NULL," +
            "${DbContract.PRIORITY} INTEGER NOT NULL," +
            "${DbContract.TYPE} INTEGER NOT NULL)"

private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${DbContract.TABLE_NAME}"

class NotesSQLiteDBHelper(appContext: Context) : SQLiteOpenHelper(
    appContext,
    DbContract.DATABASE_NAME,
    null,
    1,
    DatabaseErrorHandler { Log.e(NotesSQLiteDBHelper::javaClass.name, "Error with $it") }
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun getNotes(): List<NoteEntity> {
        val list = mutableListOf<NoteEntity>()
        val sortOrder = "${DbContract.DATE} DESC"

        val cursor = readableDatabase.query(
            DbContract.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            sortOrder
        )

        cursor.use {
            while (it.moveToNext()) {
                list.add(
                    NoteEntity(
                        id = it.getLong(it.getColumnIndexOrThrow(DbContract.COLUMN_ID)).toInt(),
                        title = it.getString(it.getColumnIndexOrThrow(DbContract.TITLE)),
                        color = it.getLong(it.getColumnIndexOrThrow(DbContract.COLOR_RES)).toInt(),
                        date = it.getLong(it.getColumnIndexOrThrow(DbContract.DATE)),
                        description = it.getString(it.getColumnIndexOrThrow(DbContract.DESCRIPTION)),
                        priority = it.getLong(it.getColumnIndexOrThrow(DbContract.PRIORITY))
                            .toInt(),
                        type = it.getLong(it.getColumnIndexOrThrow(DbContract.TYPE)).toInt()
                    )
                )
            }
        }
        return list.toList()
    }

    fun insertNote(noteEntity: NoteEntity) {
        val content = ContentValues().apply {
            put(DbContract.TITLE, noteEntity.title)
            put(DbContract.COLOR_RES, noteEntity.color)
            put(DbContract.DATE, noteEntity.date)
            put(DbContract.DESCRIPTION, noteEntity.description)
            put(DbContract.PRIORITY, noteEntity.priority)
            put(DbContract.TYPE, noteEntity.type)
        }

        writableDatabase.insert(DbContract.TABLE_NAME, null, content)
    }

    fun delete(id: String) {
        val selection = "${DbContract.COLUMN_ID} LIKE ?"
        val selectionArgs = arrayOf(id)
        writableDatabase.delete(DbContract.TABLE_NAME, selection, selectionArgs)
    }


    fun clear(){
        writableDatabase.execSQL("DELETE FROM ${DbContract.TABLE_NAME}")
    }
}