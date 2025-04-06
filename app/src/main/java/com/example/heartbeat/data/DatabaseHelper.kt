package com.example.heartbeat.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "heartbeat.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_NAME = "name"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_EMAIL TEXT UNIQUE, " +
                "$COLUMN_PASSWORD TEXT, " +
                "$COLUMN_NAME TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun addUser(user: User): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_EMAIL, user.email)
        values.put(COLUMN_PASSWORD, user.password)
        values.put(COLUMN_NAME, user.name)
        return db.insert(TABLE_USERS, null, values)
    }

    fun getUser(email: String, password: String): User? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_ID, COLUMN_EMAIL, COLUMN_PASSWORD, COLUMN_NAME),
            "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(email, password),
            null,
            null,
            null
        )

        return if (cursor.moveToFirst()) {
            val user = User(
                id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }

    fun isEmailExists(email: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_ID),
            "$COLUMN_EMAIL = ?",
            arrayOf(email),
            null,
            null,
            null
        )
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }
} 