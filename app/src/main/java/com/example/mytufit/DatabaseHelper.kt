package com.example.mytufit

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "MyTUFit.db"
        const val DATABASE_VERSION = 2

        // Users Table
        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Create Users Table
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_EMAIL TEXT NOT NULL UNIQUE,
                $COLUMN_PASSWORD TEXT NOT NULL
            )
        """
        db?.execSQL(createUsersTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }


    fun loginUser(email: String, password: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))

        val isLoggedIn = cursor.count > 0
        cursor.close()
        db.close()
        return isLoggedIn
    }

    fun registerUser(name: String, email: String, password: String): Boolean {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
        }

        val result = db.insert(TABLE_USERS, null, contentValues)
        db.close()
        return result != -1L // Returns true if the insertion is successful
    }

}
