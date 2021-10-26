package com.example.notesappsaveonly

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBHlper(context: Context):SQLiteOpenHelper(context,"NotesDatabase",null,1) {
    var sqLiteDatabase :SQLiteDatabase = writableDatabase
    companion object{
        private const val TABLE_NOTES = "NotesTable"
        private const val KEY_ID = "_id"
        private const val KEY_NOTE = "note"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL("create table $TABLE_NOTES ($KEY_ID INTEGER PRIMARY KEY,$KEY_NOTE text)")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun saveData(note:Notes) {
        val cv = ContentValues()
        cv.put(KEY_NOTE,note.note)
        sqLiteDatabase.insert(TABLE_NOTES,null,cv)
    }

    @SuppressLint("Range", "Recycle")
    fun viewNotes(): ArrayList<Notes>{
        val items = arrayListOf<Notes>()
        val selectQuery = "SELECT * FROM $TABLE_NOTES"

            var cursor : Cursor = sqLiteDatabase.rawQuery(selectQuery,null)

        if(cursor.count<1) println("no data found")
        else{ (cursor.moveToFirst())
                do {
                   val id = cursor.getInt(0)
                   val note = cursor.getString(1)

                    items.add(Notes(id,note))
                } while (cursor.moveToNext())
            }
            return items
    }

    fun updateNote(note: Notes) {
        val cv = ContentValues()
        cv.put(KEY_NOTE, note.note)
        sqLiteDatabase.update(TABLE_NOTES, cv, "$KEY_ID =${note.id}", null)
    }

    fun deleteNote(note: Notes){
        sqLiteDatabase.delete(TABLE_NOTES, "$KEY_ID =${note.id}", null)
    }
}