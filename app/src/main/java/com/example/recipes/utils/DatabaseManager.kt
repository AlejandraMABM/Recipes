package com.example.recipes.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.recipes.data.RecipeName

class DatabaseManager(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "RecipeDB.db"


        private const val SQL_CREATE_TABLE =
            "CREATE TABLE ${RecipeName.TABLE_NAME} (" +
                    "${RecipeName.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${RecipeName.COLUMN_NAME} TEXT," +
                    "${RecipeName.COLUMN_INGREDIENTS} TEXT," +
                    "${RecipeName.COLUMN_IMAGE} TEXT)"

        private const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${RecipeName.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onDestroy(db)
        onCreate(db)
    }

    private fun onDestroy(db: SQLiteDatabase) {
        db.execSQL(SQL_DELETE_TABLE)
    }
}