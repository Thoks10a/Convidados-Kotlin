package com.example.convidados.repository

import android.content.ContentValues
import android.content.Context
import android.widget.Toast
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.model.GuestModel
import com.google.android.material.snackbar.Snackbar

// TODO: Manipulate database data

class GuestRepository private constructor(context: Context) {

    // TODO: Check if there is only one GuestRepository instance in another class
    private val guestDataBase: GuestDataBase = GuestDataBase(context)

    // Singleton
    companion object {
        private lateinit var repository: GuestRepository
        fun getInstance(context: Context): GuestRepository {
            if (!Companion::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun insert(guest: GuestModel): Boolean {
        // TODO: Insert in to the database

        return try {
            val db = guestDataBase.writableDatabase

            val presence = if (guest.presence) 1 else 0
            val values = ContentValues()
            values.put(DataBaseConstants.Guest.Columns.name, guest.name)
            values.put(DataBaseConstants.Guest.Columns.presence, presence)

            db.insert(DataBaseConstants.Guest.table_name, null, values)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }

    fun update(guest: GuestModel): Boolean {
        // TODO: Update the guest in to the database
        return try {
            val db = guestDataBase.writableDatabase
            val presence = if (guest.presence) 1 else 0
            val values = ContentValues()
            values.put(DataBaseConstants.Guest.Columns.name, guest.name)
            values.put(DataBaseConstants.Guest.Columns.presence, guest.presence)
            val selection = DataBaseConstants.Guest.Columns.id + " = ?"
            val args = arrayOf(guest.id.toString())
            db.update(DataBaseConstants.Guest.table_name, values, selection, args)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun delete(id: Int): Boolean {
        // TODO: Delete the guest in to the database
        return try {
            val db = guestDataBase.writableDatabase

            val selection = DataBaseConstants.Guest.Columns.id + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.Guest.table_name, selection, args)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getAll(): List<GuestModel> {

        // TODO: Get all guests from the database
        //val list = mutableListOf<GuestModel>()
        val list: MutableList<GuestModel> = ArrayList()
        return try {
            val db = guestDataBase.readableDatabase

            // Colunas que serão retornadas
            val projection = arrayOf(
                DataBaseConstants.Guest.Columns.id,
                DataBaseConstants.Guest.Columns.name,
                DataBaseConstants.Guest.Columns.presence
            )

            // Linha única
            // Cursor cursor = db.rawQuery("select * from Guest", null);

            // Faz a seleção
            val cursor = db.query(
                DataBaseConstants.Guest.table_name,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.Guest.Columns.id))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.Guest
                        .Columns.name))
                    val presence = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.Guest
                        .Columns.presence)) == 1)

                    val guest = GuestModel(id, name, presence)
                    list.add(guest)
                }

                // Como verificar se um valor é nulo
                // cursor.isNull(cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))
            }

            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    fun get(id: Int): GuestModel?{
         var guest: GuestModel? = null
        try {
            val db = guestDataBase.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.Guest.Columns.id,
                DataBaseConstants.Guest.Columns.name,
                DataBaseConstants.Guest.Columns.presence
            )
            val selection = DataBaseConstants.Guest.Columns.id + " = ?"
            val args = arrayOf(id.toString())
            val cursor = db.query(
                DataBaseConstants.Guest.table_name,
                projection,
                selection,
                args,
                null,
                null,
                null
            )
            if(cursor != null && cursor.count > 0){
                while (cursor.moveToNext()) {
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.Guest
                        .Columns.name))
                    val presence = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.Guest
                        .Columns.presence)))
                    guest = GuestModel(id, name, presence == 1)
                }
            }
            cursor.close()
        }catch (e: Exception) {
            e.printStackTrace()
            return guest
        }
        return guest
    }

    fun getPresent(): List<GuestModel> {

        // TODO: Get all present guests from the database
        val list = mutableListOf<GuestModel>()
        try{
            val db = guestDataBase.readableDatabase
            // Other way to get all present guests from the database
            val cursor = db.rawQuery("SELECT * FROM ${DataBaseConstants.Guest.table_name} WHERE ${DataBaseConstants.Guest.Columns.presence} = 1", null)

            if (cursor != null && cursor.count > 0){
                // TODO: Get all present guests from the database, based in the cursor and args
                //  (based in
                //  args order)
                while(cursor.moveToNext()){
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.Guest.Columns.id))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.Guest.Columns.name))
                    val presence = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.Guest.Columns.presence))
                    list.add(GuestModel(id, name, presence == 1))
                }
            }
            cursor.close()

        }catch (e: Exception) {
            e.printStackTrace()
            return list
        }
        return list
    }

    fun getAbsent(): List<GuestModel> {

        // TODO: Get all present guests from the database
        val list = mutableListOf<GuestModel>()
        try{
            val db = guestDataBase.readableDatabase
            // Other way to get all present guests from the database
            val cursor = db.rawQuery("SELECT * FROM ${DataBaseConstants.Guest.table_name} WHERE " +
                    "${DataBaseConstants.Guest.Columns.presence} = 0", null)

            if (cursor != null && cursor.count > 0){
                // TODO: Get all present guests from the database, based in the cursor and args
                //  (based in
                //  args order)
                while(cursor.moveToNext()){
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.Guest.Columns.id))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.Guest.Columns.name))
                    val presence = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.Guest.Columns.presence))
                    list.add(GuestModel(id, name, presence == 1))
                }
            }
            cursor.close()

        }catch (e: Exception) {
            e.printStackTrace()
            return list
        }
        return list
    }

}
