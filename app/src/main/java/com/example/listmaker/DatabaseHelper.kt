package com.example.listmaker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME = "MYDB"
val TABLE_NAME = "List"
val COL_ID = "id"
val COL_VALUE = "Value"

class DatabaseHelper(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val create =
            "CREATE TABLE $TABLE_NAME($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COL_VALUE VARCHAR(255));"
        db?.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertdata(data: Data) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_VALUE, data.value)
        val res = db.insert(TABLE_NAME, null, cv)
        if (res == (-1).toLong()) {
            Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "ADDED", Toast.LENGTH_SHORT).show()
        }
    }

    fun readdata(): MutableList<Data> {
        val list = mutableListOf<Data>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val res = db.rawQuery(query, null)
        if (res.moveToFirst()) {
            do {
                val data = Data()
                data.id = res.getString(res.getColumnIndex(COL_ID)).toInt()
                data.value = res.getString(res.getColumnIndex(COL_VALUE))
                list.add(data)
            } while (res.moveToNext())
            res.close()
            db.close()
        }
        return list

    }
    fun deletedata(){
        val db=writableDatabase
        db.delete(TABLE_NAME,null,null)
        db.close()
    }
    fun deletespec(value:String){
        val db=writableDatabase
        db.delete(TABLE_NAME,"$COL_VALUE='$value'",null)
        db.close()
    }

}