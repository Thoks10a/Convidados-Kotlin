package com.example.convidados.constants

class DataBaseConstants private constructor() {
    object Guest{
        const val ID = "guestid"
        const val table_name = "Guest"
        object Columns{
            const val id = "id"
            const val name = "name"
            const val presence = "presence"
        }
    }


}