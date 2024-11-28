package com.example.todolist.data.providers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.recipes.data.RecipeName
import com.example.recipes.utils.DatabaseManager

class TaskDAO(val context: Context) {

    private lateinit var db: SQLiteDatabase

    private fun open() {
        db = DatabaseManager(context).writableDatabase
    }

    private fun close() {
        db.close()
    }

    fun getContentValues(recipeName: RecipeName): ContentValues {
        return ContentValues().apply {
            put(RecipeName.COLUMN_NAME, recipeName.name)
            put(RecipeName.COLUMN_IMAGE, recipeName.image)
            put(RecipeName.COLUMN_INGREDIENTS, recipeName.ingredients.toString())
        }
    }

    fun cursorToEntity(cursor: Cursor): RecipeName {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(RecipeName.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(RecipeName.COLUMN_NAME))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(RecipeName.COLUMN_IMAGE))
        val ingredients = cursor.getInt(cursor.getColumnIndexOrThrow(RecipeName.COLUMN_INGREDIENTS))
//TODO = "A revisar"
        return RecipeName(id.toInt(), name, image, ingredients)
    }

    fun insert(task: Task) {
        open()

        // Create a new map of values, where column names are the keys
        val values = getContentValues(task)

        try {
            // Insert the new row, returning the primary key value of the new row
            val id = db.insert(Task.TABLE_NAME, null, values)
        } catch (e: Exception) {
            Log.e("DB", e.stackTraceToString())
        } finally {
            close()
        }
    }

    fun update(task: Task) {
        open()

        // Create a new map of values, where column names are the keys
        val values = getContentValues(task)

        try {
            // Update the existing rows, returning the number of affected rows
            val updatedRows = db.update(Task.TABLE_NAME, values, "${Task.COLUMN_ID} = ${task.id}", null)
        } catch (e: Exception) {
            Log.e("DB", e.stackTraceToString())
        } finally {
            close()
        }
    }

    fun delete(task: Task) {
        open()

        try {
            // Delete the existing row, returning the number of affected rows
            val deletedRows = db.delete(Task.TABLE_NAME, "${Task.COLUMN_ID} = ${task.id}", null)
        } catch (e: Exception) {
            Log.e("DB", e.stackTraceToString())
        } finally {
            close()
        }
    }

    fun findById(id: Long) : Task? {
        open()

        try {
            val cursor = db.query(
                Task.TABLE_NAME,                    // The table to query
                Task.COLUMN_NAMES,                  // The array of columns to return (pass null to get all)
                "${Task.COLUMN_ID} = $id",  // The columns for the WHERE clause
                null,                   // The values for the WHERE clause
                null,                       // don't group the rows
                null,                         // don't filter by row groups
                null                         // The sort order
            )

            if (cursor.moveToNext()) {
                return cursorToEntity(cursor)
            }
        } catch (e: Exception) {
            Log.e("DB", e.stackTraceToString())
        } finally {
            close()
        }
        return null
    }

    fun findAll() : List<Task> {
        open()

        var list: MutableList<Task> = mutableListOf()

        try {
            val cursor = db.query(
                Task.TABLE_NAME,                    // The table to query
                Task.COLUMN_NAMES,                  // The array of columns to return (pass null to get all)
                null,                       // The columns for the WHERE clause
                null,                   // The values for the WHERE clause
                null,                       // don't group the rows
                null,                         // don't filter by row groups
                null                         // The sort order
            )

            while (cursor.moveToNext()) {
                val task = cursorToEntity(cursor)
                list.add(task)
            }
        } catch (e: Exception) {
            Log.e("DB", e.stackTraceToString())
        } finally {
            close()
        }
        return list
    }

}