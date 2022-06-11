package com.example.convidados.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados.model.GuestModel

// TODO: Manipulate database data

class GuestRepository private constructor(context: Context) {

    // TODO: Check if there is only one GuestRepository instance in another class
    private val guestDataBase = GuestDataBase(context)

    // Singleton
    companion object {
        private lateinit var repository: GuestRepository
        fun getInstance(context: Context): GuestRepository {
            if(!Companion::repository.isInitialized){
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun insert(guest: GuestModel): Boolean{
        // TODO: Insert in to the database

        return try{
            val db = guestDataBase.writableDatabase

            val presence = if(guest.presence) 1 else 0
            val values = ContentValues()
            values.put("name", guest.name)
            values.put("presence", presence)

            db.insert("Guest",null,values)

            true
        }catch(e: Exception){
            e.printStackTrace()
            false
        }

    }

    fun update(){

    }
}
