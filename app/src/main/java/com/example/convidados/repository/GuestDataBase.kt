package com.example.convidados.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.convidados.constants.DataBaseConstants

class GuestDataBase(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {

    // TODO: This is the database connection

    companion object {
        private const val NAME = "guestdb"
        private const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // TODO: Create table
        db.execSQL("CREATE TABLE " + DataBaseConstants.Guest.table_name + " (" +
                DataBaseConstants.Guest.Columns.id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DataBaseConstants.Guest.Columns.name + " TEXT NOT NULL," +
                DataBaseConstants.Guest.Columns.presence + " INTEGER NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}
