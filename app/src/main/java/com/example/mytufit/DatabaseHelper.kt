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

        // Posts Table
        const val TABLE_POSTS = "posts"
        const val POST_ID = "post_id"
        const val POST_USERNAME = "username"
        const val POST_CONTENT = "content"
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

        // Create Posts Table
        val createPostsTable = """
            CREATE TABLE $TABLE_POSTS (
                $POST_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $POST_USERNAME TEXT NOT NULL,
                $POST_CONTENT TEXT NOT NULL
            )
        """
        db?.execSQL(createPostsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_POSTS")
        onCreate(db)
    }

    // Add a new post
    fun addPost(username: String, content: String): Boolean {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(POST_USERNAME, username)
        contentValues.put(POST_CONTENT, content)

        val result = db.insert(TABLE_POSTS, null, contentValues)
        db.close()
        return result != -1L
    }

    // Get all posts
    fun getPosts(): List<Pair<String, String>> {
        val db = readableDatabase
        val query = "SELECT $POST_USERNAME, $POST_CONTENT FROM $TABLE_POSTS"
        val cursor = db.rawQuery(query, null)

        val posts = mutableListOf<Pair<String, String>>()
        if (cursor.moveToFirst()) {
            do {
                val username = cursor.getString(cursor.getColumnIndexOrThrow(POST_USERNAME))
                val content = cursor.getString(cursor.getColumnIndexOrThrow(POST_CONTENT))
                posts.add(Pair(username, content))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return posts
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
