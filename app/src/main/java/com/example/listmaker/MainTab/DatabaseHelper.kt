package com.example.listmaker.MainTab

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.listmaker.DAY.Data
import com.example.listmaker.Month.MonthData

val DATABASE_NAME = "MYDB"
val TABLE_NAME = "List"
val COL_DATE = "date"
val COL_ID = "id"
val COL_VALUE = "Value"
val COL_MONTHDAY = "Monthday"
val TABLE_NAME2 = "Monthwise"
val COL_VALUE2 = "Monthvalue"
val COL_MONTH = "com/example/listmaker/Month"

val COL_ID2 = "id2"
val COL_ID3 = "id3"
val COL_DAYLIMIT = "daylimit"
val COL_MONTHLIMIT = "monthlimit"
val TABLE_NAME3 = "table3"

class DatabaseHelper(var context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null, 4) {
    override fun onCreate(db: SQLiteDatabase?) {
        val create1 =
            "CREATE TABLE $TABLE_NAME($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COL_VALUE VARCHAR(255),$COL_DATE VARCHAR,$COL_MONTHDAY VARCHAR(255));"
        db?.execSQL(create1)
        val create2 =
            "CREATE TABLE $TABLE_NAME2 ($COL_ID2 INTEGER PRIMARY KEY AUTOINCREMENT,$COL_MONTH VARCHAR(255),$COL_VALUE2 VARCHAR(255));"
        db?.execSQL(create2)
        val create3 =
            "CREATE TABLE $TABLE_NAME3($COL_ID3 INTEGER PRIMARY KEY AUTOINCREMENT,$COL_DAYLIMIT INTEGER,$COL_MONTHLIMIT INTEGER);"
        db?.execSQL(create3)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun insertdata(data: Data) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_DATE, data.date)
        cv.put(COL_VALUE, data.value)
        cv.put(COL_MONTHDAY, data.month)
        val res = db.insert(TABLE_NAME, null, cv)
        if (res == (-1).toLong()) {
            Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()
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
                data.date = res.getString(res.getColumnIndex(COL_DATE))
                data.month = res.getString(res.getColumnIndex(COL_MONTHDAY))
                list.add(data)
            } while (res.moveToNext())
            res.close()
            db.close()
        }
        return list

    }

    fun deletedata() {
        val db = writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }

    fun deletespec(value: String) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COL_DATE='$value'", null)
        db.close()
    }

    fun editdata(prev: String, new: String) {
        Log.i("i", "update")
        val db = readableDatabase
        val cv = ContentValues()
        cv.put(COL_VALUE, new)
        db.update(TABLE_NAME, cv, "$COL_DATE='$prev'", null)
        db.close()
    }

    fun updatedata(prev: String, new: String, date: String) {
        val db = readableDatabase
        val cv = ContentValues()
        val sum = prev.toInt() + new.toInt()
        val sumstr = sum.toString()
        cv.put(COL_VALUE, sumstr)
        db.update(TABLE_NAME, cv, "$COL_DATE='$date'", null)
        db.close()
    }

    fun insertmonth(monthdata: MonthData) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_MONTH, monthdata.month)
        cv.put(COL_VALUE2, monthdata.monthvalue)
        db.insert(TABLE_NAME2, null, cv)
        db.close()
    }

    fun monthread(): MutableList<MonthData> {
        val db = readableDatabase
        val res = db.rawQuery("SELECT * FROM $TABLE_NAME2", null)
        val list = mutableListOf<MonthData>()
        if (res.moveToFirst()) {
            do {
                val m = MonthData()
                m.month = res.getString(res.getColumnIndex(COL_MONTH))
                m.monthvalue = res.getString(res.getColumnIndex(COL_VALUE2))
                list.add(m)
            } while (res.moveToNext())

        }
        res.close()
        db.close()
        return list
    }

    fun monthdel() {
        val db = writableDatabase
        db.delete(TABLE_NAME2, null, null)
        db.close()
    }

    fun monthupdate(prev: String, new: String, month: String) {
        val db = readableDatabase
        val cv = ContentValues()
        val sum = prev.toInt() + new.toInt()
        val sumstr = sum.toString()
        cv.put(COL_VALUE2, sumstr)
        db.update(TABLE_NAME2, cv, "$COL_MONTH='$month'", null)
        db.close()
    }

    fun monthdelspec(month: String) {
        val db = writableDatabase
        db.delete(TABLE_NAME2, "$COL_MONTH= '$month'", null)
        db.close()
    }

    fun deductmonth(monthvalue: String, dayvalue: String, month: String) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_VALUE2, monthvalue.toInt() - dayvalue.toInt())
        db.update(TABLE_NAME2, cv, "$COL_MONTH='$month'", null)
        db.close()
    }

    fun editmonth(month: String, prev: String, new: String, own: String) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_VALUE2, (new.toInt() - prev.toInt()) + own.toInt())
        db.update(TABLE_NAME2, cv, "$COL_MONTH ='$month'", null)
        db.close()

    }

    fun limitinsert(limit: Limit) {
        val db = writableDatabase
        db.delete(TABLE_NAME3, null, null)
        val cv = ContentValues()
        cv.put(COL_DAYLIMIT, limit.daywise_limit)
        cv.put(COL_MONTHLIMIT, limit.monthwise_limit)
        db.insert(TABLE_NAME3, null, cv)
        db.close()
    }

    fun limitread(): Limit {
        val db = readableDatabase
        val res = db.rawQuery("SELECT * FROM $TABLE_NAME3", null)
        val limit = Limit()
        if(res.moveToFirst()) {
            limit.daywise_limit = res.getString(res.getColumnIndex(COL_DAYLIMIT)).toInt()
            limit.monthwise_limit = res.getString(res.getColumnIndex(COL_MONTHLIMIT)).toInt()
        }
    return limit
}
}