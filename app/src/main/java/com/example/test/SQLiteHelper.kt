package com.example.test

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.test.Constants.CREATE_TBL_STUDENT
import com.example.test.Constants.DATABASE_NAME
import com.example.test.Constants.DATABASE_VERSION
import com.example.test.Constants.DROP_TABLE_IF_EXISTS
import com.example.test.Constants.EMAIL
import com.example.test.Constants.ID
import com.example.test.Constants.NAME
import com.example.test.Constants.SELECT_ALL_TABLE
import com.example.test.Constants.TBL_STUDENT

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TBL_STUDENT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL(DROP_TABLE_IF_EXISTS)
        onCreate(db)
    }

    fun insertStudent(std: StudentModel): Long{
        val db = this.writableDatabase
        val contentValue = ContentValues().apply {
            put(ID, std.id)
            put(NAME, std.name)
            put(EMAIL, std.email)
        }

        val success = db.insert(TBL_STUDENT, null, contentValue)
        db.close()
        return success
    }

    @SuppressLint("Range", "Recycle")
    fun getAllStudents(): ArrayList<StudentModel>{
        val stdList: ArrayList<StudentModel> = ArrayList()
        val db = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(SELECT_ALL_TABLE, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(SELECT_ALL_TABLE)
            return ArrayList()
        }

        if (cursor.moveToFirst()){
            do {
                val id: Int = cursor.getInt(cursor.getColumnIndex("id"))
                val name: String = cursor.getString(cursor.getColumnIndex("name"))
                val email: String = cursor.getString(cursor.getColumnIndex("email"))

                val std = StudentModel(id = id, name = name, email = email)
                stdList.add(std)
            } while (cursor.moveToNext())
        }
        return stdList
    }

    fun upgradeStudent(std: StudentModel) : Int{
        val db = this.writableDatabase

        val contentValue = ContentValues().apply {
            put(ID, std.id)
            put(NAME, std.name)
            put(EMAIL, std.email)
        }
        val success = db.update(TBL_STUDENT, contentValue, "$ID= ${std.id}", null )
        db.close()
        return success
    }

    fun deleteStudent(id: Int): Int {
        val db = this.writableDatabase

        val success = db.delete(TBL_STUDENT, "$ID = $id", null)
        db.close()
        return success
    }
}