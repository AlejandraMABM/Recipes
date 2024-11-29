package com.example.todolist.data.providers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.recipes.data.RecipeName
import com.example.recipes.utils.DatabaseManager

class RecipeNameDAO(val context: Context) {

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
            put(RecipeName.COLUMN_INGREDIENTS, recipeName.ingredients.toString())
            put(RecipeName.COLUMN_IMAGE, recipeName.image)

        }
    }
    //modificar esta operacion
    fun cursorToEntity(cursor: Cursor): RecipeName {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(RecipeName.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(RecipeName.COLUMN_NAME))
        val ingredients = cursor.getString(cursor.getColumnIndexOrThrow(RecipeName.COLUMN_INGREDIENTS))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(RecipeName.COLUMN_IMAGE))

//TODO = "A revisar"
       return RecipeName(id.toInt(), name, ingredients, image)
    }

    fun insert(recipeName: RecipeName) {
        open()

        // Create a new map of values, where column names are the keys
        val values = getContentValues(recipeName)

        try {
            // Insert the new row, returning the primary key value of the new row
            val id = db.insert(RecipeName.TABLE_NAME, null, values)
        } catch (e: Exception) {
            Log.e("DB", e.stackTraceToString())
        } finally {
            close()
        }
    }

    fun update(recipeName: RecipeName) {
        open()

        // Create a new map of values, where column names are the keys
        val values = getContentValues(recipeName)

        try {
            // Update the existing rows, returning the number of affected rows
            val updatedRows = db.update(RecipeName.TABLE_NAME, values, "${RecipeName.COLUMN_ID} = ${recipeName.id}", null)
        } catch (e: Exception) {
            Log.e("DB", e.stackTraceToString())
        } finally {
            close()
        }
    }

    fun delete(task: RecipeName) {
        open()

        try {
            // Delete the existing row, returning the number of affected rows
            val deletedRows = db.delete(RecipeName.TABLE_NAME, "${RecipeName.COLUMN_ID} = ${task.id}", null)
        } catch (e: Exception) {
            Log.e("DB", e.stackTraceToString())
        } finally {
            close()
        }
    }

    fun findById(id: Long) : RecipeName? {
        open()

        try {
            val cursor = db.query(
                RecipeName.TABLE_NAME,                    // The table to query
                RecipeName.COLUMN_NAME,                  // The array of columns to return (pass null to get all)
                "${RecipeName.COLUMN_ID} = $id",  // The columns for the WHERE clause
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

    fun findAll() : List<RecipeName> {
        open()

        var list: MutableList<RecipeName> = mutableListOf()

        try {
            val cursor = db.query(
                RecipeName.TABLE_NAME,                    // The table to query
                RecipeName.COLUMN_NAME,                  // The array of columns to return (pass null to get all)
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